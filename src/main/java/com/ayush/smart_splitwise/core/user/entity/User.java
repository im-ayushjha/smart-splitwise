package com.ayush.smart_splitwise.core.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phoneNumber;

    private boolean emailVerified;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Builder(){}

        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phoneNumber;
        private boolean emailVerified;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder emailVerified(boolean emailVerified){
            this.emailVerified = emailVerified;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt){
            this.updatedAt = updatedAt;
            return this;
        }

        public User build(){
            return new User(null, firstName, lastName, email, password, phoneNumber, emailVerified, createdAt, updatedAt);
        }
    }
}