package fr.glowstoner.sylvanus.boot;

import java.io.File;
import java.io.IOException;

import fr.glowstoner.sylvanus.Sylvanus;
import fr.glowstoner.sylvanus.profile.PublicProfile;

public class SylvanusBoot {
	
	private SylvanusConfig config;
	private File folder;
	
	public SylvanusBoot() {
		
	}

	public void init() {
		System.out.println("Démarrage du programme de démarage ...");
		
		System.out.println("Vérification de l'arborescence des fichiers ...");
		this.initFiles();
		System.out.println("Fait.");
		
		System.out.println("Vérification des profiles ...");
		this.initProfiles();
		System.out.println("Fait.");
		
		System.out.println("Fin du programme de démarrage.");
	}
	
	public void initFiles() {
		if(this.hasFolder()) {
			System.out.println("Dossier existant ! :)");
		}else {
			System.out.println("Dossier inexistant ! :( Création ...");
			
			this.createFolder();
		}
		
		this.folder = new File("conf");
		
		this.config = new SylvanusConfig(this.folder);
		
		try {
			this.config.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.config.showData();
		
		Sylvanus.getInstance().setProperties(this.config.getData());
	}
	
	public void initProfiles() {
		System.out.println("Définition du profil public ...");
		Sylvanus.getInstance().setPublicProfile(new PublicProfile());
		
		System.out.println("Fait. La clé du profil public à été défini à "+
				Sylvanus.getInstance().getPublicProfile().getKey());
		
		System.out.println("Définition du profil privé ...");
		
		try {
			System.out.println((Sylvanus.getInstance().checkPrivateProfile()) ? 
						"Fichier profil trouvé ! La clé du profil est "+
						Sylvanus.getInstance().getPrivateProfile().getKey()+
						". Faîtes attention avant de la partager !" :
						"Fichier profil non-trouvé ! Nouveau fichier créé. La clé du nouveau profil est "+
						Sylvanus.getInstance().getPrivateProfile().getKey()+". Faîtes attention avant de la partager !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SylvanusConfig getConfig() {
		return this.config;
	}
	
	public File getFolder() {
		return this.folder;
	}
	
	private boolean hasFolder() {
		return new File("conf").exists();
	}
	
	private void createFolder() {
		new File("conf").mkdirs();
	}
}