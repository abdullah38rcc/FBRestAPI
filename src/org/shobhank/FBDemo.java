package org.shobhank;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;


public class FBDemo {	
	public static void main(String[] args) {
		FacebookClient facebookClient = new DefaultFacebookClient(args[0]);
		User user = facebookClient.fetchObject("me", User.class);
		System.out.println(user.getFirstName());
	}
}
