package com.example.lab5android;

public class TodoModel {
    private    String _id ;
    private    String todo ;

    public TodoModel(String _id, String todo) {
        this._id = _id;
        this.todo = todo;
    }

    public TodoModel(String todo) {
        this.todo = todo;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
