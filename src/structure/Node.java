package structure;

import common.Token;

public class Node {
    private String value;
    private Token token;
    private Node left;
    private Node right;

    public Node(String value, Token token) {
        this.value = value;
        right = null;
        left = null;
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}