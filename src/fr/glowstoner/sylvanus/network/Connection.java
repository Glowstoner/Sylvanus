package fr.glowstoner.sylvanus.network;

import java.io.IOException;

public abstract interface Connection {
	
	void start() throws IOException;
	
	void close() throws IOException;

}
