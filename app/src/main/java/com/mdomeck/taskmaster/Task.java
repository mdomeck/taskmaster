package com.mdomeck.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

        @PrimaryKey(autoGenerate = true)
        long id;

        public String title;
        public String body;
        public String state;

        public Task(String title, String body, String state){
            this.title = title;
            this.body = body;
            this.state = state;
        }
}
