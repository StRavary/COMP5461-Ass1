
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver {

    /** 
     * main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	 /*******************************************************************************************************************************************
    	 */
        Network objNetwork = new Network("network");
        Server objServer = new Server();
        Client clientSending = new Client("sending");
        Client clientReceiving = new Client("receiving");
        
        // Create all 4 threads
        Thread networkThread = new Thread(() -> objNetwork.run());
        Thread serverThread = new Thread(() -> objServer.run());
        Thread sendingThread = new Thread(() -> clientSending.run());
        Thread receivingThread = new Thread(() -> clientReceiving.run());
        
        // Start all 4
        networkThread.start();
        serverThread.start();
        sendingThread.start();
        receivingThread.start();
        
        // Wait for all to complete
        try {
            sendingThread.join();
            receivingThread.join();
            objNetwork.disconnect(objNetwork.getClientIP());
            serverThread.join();
            objNetwork.disconnect(objNetwork.getServerIP());
            networkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n Terminating network thread - Client " + objNetwork.getClientConnectionStatus() + " Server " + objNetwork.getServerConnectionStatus());
    	
    }
}
