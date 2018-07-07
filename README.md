# Laravel jEcho Client
A Java client compatible with Laravel Echo Server

### What is it?
Laravel jEcho Client uses the java-based [socket.io-client](https://github.com/nkzawa/socket.io-client) library
developed by [nkzawa](https://github.com/nkzawa) to easily interface your Java application to a
[Laravel Echo Server](https://github.com/tlaverdure/laravel-echo-server).

### Compatibility
* Socket.IO

### Getting started
Since this library works as a wrapper, it's mandatory to properly configure your Laravel environment.
Keep in mind that this library works only using the **auth:api** guard,
as we need to pass the api key to let it work.
You can follow these links as long as they can help you with the configuration:
* [How to use Laravel with Socket.IO](https://medium.com/@adnanxteam/how-to-use-laravel-with-socket-io-e7c7565cc19d)
* [HOWTO: broadcasting, laravel-echo, laravel-echo-server and JWT](https://laravel.io/forum/10-09-2016-howto-broadcasting-laravel-echo-laravel-echo-server-and-jwt)

Once you have a fully working Laravel Echo environment, you can proceed with the next steps.

Additional information on broadcasting with Laravel can be found on the official docs: https://laravel.com/docs/master/broadcasting

### Setup
We need to include the Laravel jEcho Client as a library inside our project

##### Gradle
1. Add the JitPack repository to your build file
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.AngeloAvv:laravel-jecho-client:0.1.1'
}
```

##### Maven
1. Add the JitPack repository to your build file
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
2. Add the dependency
```maven
<dependency>
    <groupId>com.github.AngeloAvv</groupId>
    <artifactId>laravel-jecho-client</artifactId>
    <version>0.1.1</version>
</dependency>
```

Next you need to create an Echo instance as it follows:
```java
Options options = new Options();
options.url = "http://localhost:6001";
options.namespace = "App.Events";
options.token = "apiToken";

Echo instance = new Echo(options);
```

When you create a new Echo instance, the client tries to connect to the Socket.IO
server. In this phase your application must be able to connect to the network.

### Listening to events
You can setup your Echo instance to listen for a specific event registered for
a specific channel using the listen method.
```java
instance.listen("channel-name", "event", new Channel.OnEvent<JSONObject>() {
    public void onEvent(JSONObject arg) {

    }
});
```

### Joining channels
The library is able to satisfy almost all of the Laravel Echo behaviors. You can join
to private, presence and public channels. The code looks the same as the js implementation.

##### Joining Private Channels
```java
instance.privateChannel("channel-name")
.listen("event", new Channel.OnEvent<JSONObject>() {
    @Override
    public void onEvent(JSONObject arg) {

    }
});
```
##### Joining Presence Channels
```java
instance.join("channel-name")
.here(new PresenceChannel.OnUsersHereListener() {
    @Override
    public void onUsersHere(int... ids) {

    }
})
.joining(new PresenceChannel.OnUserJoiningListener() {
    @Override
    public void onUserJoining(int id) {

    }
})
.leaving(new PresenceChannel.OnUserLeavingListener() {
    @Override
    public void onUserLeaving(int id) {

    }
});
```

#### Joining Public Channels
```java
instance.channel("channel-name")
    .listen("event", new Channel.OnEvent<JSONObject>() {
        @Override
        public void onEvent(JSONObject arg) {

        }
    });
```

The event listener supports Java Generics, so you can switch between JSONObject
and JSONArray class types to satisfy your needs.

##### Leaving Channels
When you're done, remember to leave the channel using the following syntax:
```java
instance.leave("channel-name");
```

### Disconnecting
Since Socket.IO is based on WebSockets, you need to close the connection when
everything is done.
```java
instance.disconnect();
```

### Future
* Pusher integration
* JSON event payload deserialization through Gson, Jackson or Moshi

### Features
Android shoud be fully supported.
This library is still a work-in-progress. Please feel free to submit new
issues if you encounter some problems with it.

### Licence
Laravel jEcho Client is available under the MIT license. See the LICENSE file for more info.
