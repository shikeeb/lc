// https://leetcode.com/problems/replace-words/
class Solution {
    
    class Trie {
        String word;
        boolean isWord;
        Map<Character, Trie> children = new HashMap<>();    
    }
    
    private Trie root = new Trie();
    
    private void insert(String word) {
        Trie curr = root;
        for (char c: word.toCharArray()) {
            curr.children.putIfAbsent(c, new Trie());
            curr = curr.children.get(c);
        }
        curr.isWord = true;
        curr.word = word;
    }
    
    private String replace(String word) {
        Trie curr = root;
        for (char c : word.toCharArray()) {
            Trie node = curr.children.get(c);
            
            if(node == null) {
                return word;
            }
            
            if (node.isWord) {
                return node.word;
            }
            
            curr = curr.children.get(c);
        }
        return word;
    }
    
    public String replaceWords(List<String> dictionary, String sentence) {
        for (String word : dictionary) {
            insert(word);
        }
        
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = replace(words[i]);
        }
        
        return String.join(" ", words);
    }
}