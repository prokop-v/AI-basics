class Main {
    public static void main(String[] args) throws Exception {
        hanoiBFS hanoiBFS = new hanoiBFS();
        hanoiBFS.initialize();
        hanoiBFS.solve();
        hanoiDFS hanoiDFS = new hanoiDFS();
        hanoiDFS.initialize();
        hanoiDFS.solve();
    }
}