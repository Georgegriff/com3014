/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com3014.group1.projectmatching.core;

import org.springframework.stereotype.Service;

/**
 *
 * @author George
 */
@Service("HelloService")
public class HelloService {
    
    
   public String sayHello(String name){
       return "Hello " + name;
   }
    
}
