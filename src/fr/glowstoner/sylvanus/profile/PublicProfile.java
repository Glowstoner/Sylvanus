package fr.glowstoner.sylvanus.profile;

import java.io.IOException;

import fr.glowstoner.sylvanus.Sylvanus;

public class PublicProfile extends Profile{

	private static final long serialVersionUID = -8489977290329273407L;

	public PublicProfile() {
		super(Sylvanus.getInstance().getUsername(), ProfileType.PUBLIC);
	}

	@Override
	public void serialize() throws IOException {
		//nothing to serialize
	}

	@Override
	public Profile deserialize() throws IOException {
		//nothing to deserialize
		return null;
	}
}