package org.se.lab;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServerThreadPool
{
    private final static String WEB_DIR = "./src/main/webapp";
    private final static int SERVER_PORT = 8080;
	private static final int NUMBER_OF_THREADS = 10;
	private static final Executor exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	
	public static void main(String... ags)
	{
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(SERVER_PORT);
			Logger.log("Server started...");
			Logger.log(server.toString());

			while (true)
			{
				final Socket connection = server.accept(); // wait for a connection

				Runnable task = new Runnable()
				{
					public void run()
					{						
						HttpRequestHandler handler = new HttpRequestHandler(WEB_DIR);
						handler.handleRequest(connection);						
					}
				};
				exec.execute(task);
			}
		} 
		catch(IOException e)
		{
			throw new IllegalStateException("Can't handle connection!", e);
		}
		finally
		{
			if(server != null)
			{
				try
				{
					server.close();
				}
				catch (IOException e)
				{
					throw new IllegalStateException("Can't close server socket!", e);
				}
			}	
		}
	}
}