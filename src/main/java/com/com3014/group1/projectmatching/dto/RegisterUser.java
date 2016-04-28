package com.com3014.group1.projectmatching.dto;

/**
 *
 * @author George
 */
public class RegisterUser {

        private String password;
        private User user;

        public RegisterUser() {
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

    }
