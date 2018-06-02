package fr.glowstoner.sylvanus.profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.glowstoner.sylvanus.Sylvanus;

public class PrivateProfile extends Profile{

	private static final long serialVersionUID = 8742082402512159040L;
	
	private static File file;
	
	public PrivateProfile() {
		super(new KeyGenerator().getKey(), ProfileType.PRIVATE);
		
		file = new File(Sylvanus.getInstance().getFolder(), "PRIVATEKEY.sylk");
	}
	
	public static boolean hasFile() {
		return new File(Sylvanus.getInstance().getFolder(), "PRIVATEKEY.sylk").exists();
	}
	
	public static void createFile() throws IOException {
		if(!hasFile()) {
			new File(Sylvanus.getInstance().getFolder(), "PRIVATEKEY.sylk").createNewFile();
		}
	}

	@Override
	public void serialize() throws IOException {
		if(!hasFile()) {
			createFile();
		}
		
		FileOutputStream fous = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fous);
		
		oos.writeObject(this);
		
		oos.close();
		fous.close();
	}

	@Override
	public Profile deserialize() throws IOException {
		if(!hasFile()) {
			throw new FileNotFoundException("Le fichier de la clé privée n'a pas été trouvé !");
		}
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		PrivateProfile pp = null;
		
		try {
			pp = (PrivateProfile) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ois.close();
		fis.close();
		
		return pp;
	}
}
