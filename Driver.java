
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
        Network objNetwork = new Network();
        Server objServer1 = new Server("1");
        Server objServer2 = new Server("2");
        
        Client clientSending = new Client("sending");
        Client clientReceiving = new Client("receiving");
        
      //  // Create all 5 threads
      //  Thread networkThread = new Thread(() -> objNetwork.run());
      //  Thread serverThread1 = new Thread(() -> objServer1.run());
      //  Thread serverThread2 = new Thread(() -> objServer2.run());
//
      //  Thread sendingThread = new Thread(() -> clientSending.run());
      //  Thread receivingThread = new Thread(() -> clientReceiving.run());
      //
      //  // Start all 5
      //  networkThread.start();
      //  serverThread1.start();
      //  serverThread2.start();
      //  sendingThread.start();
      //  receivingThread.start();

        //since Network, Client & Server extends Thread, we can call start on them to have them create threads automatically calling their run methods

        objNetwork.start();

        objServer1.start();
        objServer2.start();

        clientSending.start();
        clientReceiving.start();
        
        // Wait for all to complete
        try {
          //  sendingThread.join();
          //  receivingThread.join();
          //  Network.disconnect(Network.getClientIP());
          //  serverThread1.join();
          //  serverThread2.join();
          //  Network.disconnect(Network.getServerIP());
          //  networkThread.join();

            clientSending.join();
            clientReceiving.join();
            Network.disconnect(Network.getClientIP());
            objServer1.join();
            objServer2.join();
            Network.disconnect(Network.getServerIP());
            objNetwork.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }    	
    }
}
