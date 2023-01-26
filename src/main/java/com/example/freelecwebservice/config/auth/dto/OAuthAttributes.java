package com.example.freelecwebservice.config.auth.dto;

import com.example.freelecwebservice.domain.user.Role;
import com.example.freelecwebservice.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String picture;
    private String nameAttributeKey;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String name, String email, String picture, String nameAttributeKey) {
        this.attributes = attributes;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Users toEntity() {

        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
