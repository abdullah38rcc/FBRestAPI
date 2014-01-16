package org.shobhank;

import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.User;

class FbUser {
	  @Facebook
	  String uid;
	  
	  @Facebook
	  String name;

	  @Override
	  public String toString() {
	    return String.format("%s (%s)", name, uid);
	  }
	}

public class FBDemo {
	

	public static void main(String[] args) {
		FacebookClient facebookClient = new DefaultFacebookClient(args[0]);
		//args[0] is the token obtained from https://developers.facebook.com/tools/explorer
		
		//to display username
		User user = facebookClient.fetchObject("me", User.class);
		System.out.println("My name is " + user.getFirstName());
		
		//to display number of friends user has
		Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class);
		System.out.println("I have " + myFriends.getData().size() + " friends");
		
		//get first comment on first feed
		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
		System.out.println(myFeed.getData().get(0).getComments().getData().get(0).getMessage());	
		
		//fetch all my friends names	
		String querystr = "SELECT uid,first_name FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) ORDER BY first_name ASC";		
		List<FbUser> users=facebookClient.executeFqlQuery(querystr, FbUser.class);
		for(Iterator<FbUser> iter = users.iterator();iter.hasNext();){
			String FbUserId = iter.next().uid;
			querystr = "SELECT first_name,last_name FROM user WHERE uid="+FbUserId;
			System.out.println(facebookClient.executeFqlQuery(querystr, String.class).get(0));
		}
		
		
	}
}
