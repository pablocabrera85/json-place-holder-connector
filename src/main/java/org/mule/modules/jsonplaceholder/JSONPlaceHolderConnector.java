/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.jsonplaceholder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Transformer;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.Processor;

import java.io.IOException;
import java.util.List;

import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.rest.HttpMethod;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestQueryParam;
import org.mule.api.annotations.rest.RestUriParam;
import org.mule.modules.jsonplaceholder.model.Comment;
import org.mule.modules.jsonplaceholder.model.Post;
import org.mule.modules.jsonplaceholder.model.User;

/**
 * Anypoint Connector for JSONPlaceHolder Rest API
 */
@Connector(name = "json-place-holder", friendlyName = "JSONPlaceHolder")
public abstract class JSONPlaceHolderConnector {

    /**
     * BaseUrl of the JSONPlaceHolder API
     */
    @Configurable
    @Default("http://jsonplaceholder.typicode.com")
    @RestUriParam("baseUrl")
    private String baseUrl;

    /**
     * Get a list of Posts
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/posts", method = HttpMethod.GET)
    public abstract List<Post> getPosts() throws IOException;

    /**
     * Get a list of Comments from a specific post.
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/posts/{postId}/comments", method = HttpMethod.GET)
    public abstract List<Comment> getPostsComments(@RestUriParam("postId") @Default("1") Integer postId) throws IOException;

    /**
     * Get a list of Comments from a specific post.
     * 
     * This will generate a call equivalent to: <b>{baseUrl}/posts?userId=1<b/>
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/posts", method = HttpMethod.GET)
    public abstract List<Post> getPostsFromUser(@RestQueryParam("userId") @Default("1") Integer userId) throws IOException;

    /**
     * Get a list of Users
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/users", method = HttpMethod.GET)
    public abstract List<User> getUsers() throws IOException;

    /**
     * Get a list of Comments
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/comments", method = HttpMethod.GET)
    public abstract List<Comment> getComments() throws IOException;

    /**
     * Get Post by Id
     */
    @Processor
    @ReconnectOn(exceptions = { Exception.class })
    @RestCall(uri = "{baseUrl}/posts/{postId}", method = HttpMethod.GET)
    public abstract Post getPost(@RestUriParam("postId") @Default("1") Integer postId) throws IOException;

    @Transformer(sourceTypes = { String.class })
    public static Post transformJsonToBook(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Post result = mapper.readValue(json, new TypeReference<Post>() {
        });

        return result;
    }

    /**
     * Transform a string JSON representation into a list of Posts
     * 
     * Since the API Returns JSON representations, we can simply use a transformer to handle POJOs instead of string on our connector.
     * <p/>
     * DevKit will do the magic and we don't need to worry to call this transformers.
     * <p/>
     * Users of your connector will just know that they have Objects and it will be datamapper friendly
     * <p/>
     * 
     * @param json
     *            JSON representation
     * @return List of Posts
     * @throws IOException
     */
    @Transformer(sourceTypes = { String.class })
    public static List<Post> transformJsonToPosts(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Post> result = mapper.readValue(json, new TypeReference<List<Post>>() {
        });

        return result;
    }

    @Transformer(sourceTypes = { String.class })
    public static User transformJsonToUser(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        User result = mapper.readValue(json, new TypeReference<User>() {
        });

        return result;
    }

    @Transformer(sourceTypes = { String.class })
    public static List<User> transformJsonToUsers(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<User> result = mapper.readValue(json, new TypeReference<List<User>>() {
        });

        return result;
    }

    @Transformer(sourceTypes = { String.class })
    public static Comment transformJsonToComment(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Comment result = mapper.readValue(json, new TypeReference<Comment>() {
        });

        return result;
    }

    @Transformer(sourceTypes = { String.class })
    public static List<Comment> transformJsonToComments(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Comment> result = mapper.readValue(json, new TypeReference<List<Comment>>() {
        });

        return result;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}