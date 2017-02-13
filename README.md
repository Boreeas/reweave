# Reweave - Shardbound REST Api interface
This is a simple async rest library for shardbound for java and related JVM languages

## Usage
To use the api, you'll need an application id (provided by the Spiritwalk team on request, as I've been told), and an
access token. The access token can be retrieved via oauth, further information is again provided by the folks over at
Spiritwalk.

Once you have these two bits of information, create a `PublicApiConnection`. Calls on this class are ratelimited
to be nice to the shardbound servers (currently a rate of 10 requests/second with a burst of 100 requests). To implement
this, the class has a backing thread pool. Remember to close this once you don't need to use it anymore (For this 
purpose, PublicApiConnection is auto-closeable)

Kotlin example:
```kotlin
PublicApiConnection(applicationId = myApplicationId, accessToken = myAccessToken).use {
    val future = it.oauth.verifyCredentials() // Wait for ratelimit
    val userId = future.get().userId!!
    println("User id: $userId")
    val matchHistory = it.user.getMatchHistory(userId).get()
    println("Match history: $matchHistory")
}
```

Java version:
```java
try (PublicApiConnection conn = new PublicApiConnection(myApplicationId, myAccessToken)) {
    Future<LoginResult> future = conn.getOauth().verifyCredentials();
    String userId = future.get().getUserId();
    if (userId == null) throw new RuntimeException("No user id passed in login result");
    System.out.println("User id: " + userId);
    List<Game> matchHistory = conn.getUser().getMatchHistory(userId).get();
    System.out.println("Match history: " + matchHistory);
}
```

## Maven
Coming soon, I hope