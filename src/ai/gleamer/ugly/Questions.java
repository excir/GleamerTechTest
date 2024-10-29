package ai.gleamer.ugly;

import java.util.*;

public class Questions {
    final Map<String, Deque<String>> questions = new HashMap<>();
    final Map<String, List<Integer>> categories;

    public Questions(Map<String, List<Integer>> categories, int size) {
        categories.keySet().forEach(c -> {
            Deque<String> queue = new ArrayDeque<>(size);
            for (int i = 0; i < size; i++) {
                queue.offer(c + " Question " + i);
            }
            questions.put(c, queue);
        });
        this.categories = categories;
    }

    public String getCurrentCategoryFromPosition(int position) {
        Optional<Map.Entry<String, List<Integer>>> optional = categories.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(position))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get().getKey();
        }
        // Le get() pourrait lever un Null Pointer Exception mais dans notre cas ca n'est pas possible.
        return categories.keySet().stream().findFirst().get();
    }

    // Pas le temps de gérer le cas où la pile est vide. Ca retourne null.
    public String getNextQuestionInCategory(String category) {
        return questions.get(category).poll();
    }
}
