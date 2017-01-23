package com.falcon.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.falcon.messenger.database.DatabaseClass;
import com.falcon.messenger.model.Profile;

public class ProfileService {

	Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("Pri10", new Profile("Pri10", "Priten", "Vora"));
	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		return profiles.put(profile.getProfileName(), profile);
	}
	
	public Profile updateProfile(Profile profile) {
		return (profile.getProfileName().isEmpty()) ? null : profiles.put(profile.getProfileName(), profile);
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
