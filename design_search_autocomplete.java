class AutocompleteSystem {

    class Trie implements Comparable<Trie> {
        int hotCount;
        String word;
        boolean isWord;
        Map<Character, Trie> children = new HashMap<>();
        
        @Override
        public int compareTo(Trie t2) { //compare result to this
            if (this.hotCount == t2.hotCount) {
                // Questions asks for 'smaller string (ASCII)' to appear first
                // Since the collection is sorted in descending order or 'hotness'
                // we need to do the reverse comparison for the strings if the
                // hotness value matches.
                return t2.word.compareTo(this.word); 
            }
            
            return Integer.compare(this.hotCount, t2.hotCount);
        }
    }
    
    private List<Character> charList;
    private Trie root;
    
    private static final int RESULT_SIZE = 3;
    
    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new Trie();
        charList = new ArrayList<>();
        
        for(int i = 0; i < sentences.length; i++) {
            addSentence(sentences[i], times[i]);    
        }
    }
    
    private void addSentence(String sentence, int hotCount) {
        Trie curr = root;
        for (char c: sentence.toCharArray()) {
            curr.children.putIfAbsent(c, new Trie());
            curr = curr.children.get(c);
        }
        curr.hotCount = hotCount;
        curr.isWord = true;
        curr.word = sentence;
    }
        
    public List<String> input(char c) {
        if (c == '#') {
            addNewSentence(new ArrayList<Character>(charList)); // copy of list
            charList = new ArrayList<>();
            return new ArrayList<String>(); //return empty result list if we get a special char
        } 
        // only add to search query if it's not a special char
        charList.add(c);  
        List<Trie> unfilteredResults = searchTree();     
            
        // return top 3 results
        return filterList(unfilteredResults);
    }

    private void addNewSentence(List<Character> sentence) {
        StringBuilder sb = new StringBuilder();
        
        Trie curr = root;
        for (char c: sentence) {
            curr.children.putIfAbsent(c, new Trie());
            curr = curr.children.get(c);
            sb.append(c); //append each char to the string builder
        }
        int count = curr.hotCount;
        curr.hotCount = count + 1; // increment hot count
        curr.isWord = true;
        curr.word = sb.toString(); //store list of chars as word on node
    }    
    
    private List<Trie> searchTree() {
        List<Trie> results = new ArrayList<Trie>();
        
        // Find Prefix node (might be faster to store current prefix node 
        // of search query instead of searching from root each time)
        Trie curr = root;
        for (char c: charList) {
            curr = curr.children.get(c);
            if (curr == null) {
                return results;
            }
        }
                
        // Get all words under that prefix
        getWordsUnderPrefix(curr, results);    
        return results;
    }
    
    private void getWordsUnderPrefix(Trie node, List<Trie> results) {
        if (node.isWord) {
            results.add(node);
        }
        
        for (Trie childNode: node.children.values()) {
            getWordsUnderPrefix(childNode, results);
        }
    }
    
    private List<String> filterList(List<Trie> nodeList) {
        List<String> result = new ArrayList<>();
        if (nodeList.size() == 0) {
            return result;
        }
        
        Collections.sort(nodeList, Collections.reverseOrder()); //largest first (descending order)
        for (int i = 0; i < Math.min(nodeList.size(), RESULT_SIZE); i++) {
            result.add(nodeList.get(i).word);
        }
        return result;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */