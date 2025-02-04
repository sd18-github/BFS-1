// Time Complexity : O(m + n)
// Space Complexity : O(m + n)
// where m = size of prerequisites (edges), n = numCourses (vertices)
// Did this code successfully run on Leetcode : Yes

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class CourseSchedule {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer> inDegree = new ArrayList<>();
        List<List<Integer>> adjList = new ArrayList<>();

        //1. build adj list and inDegrees

        //1a. initialize adjList
        for(int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        //1b. initialize inDegrees
        for(int i = 0; i < numCourses; i++) {
            inDegree.add(0);
        }

        //1c. create adjList and set inDegrees
        for(int[] prerequisite: prerequisites) {
            // prerequisites are in the form [dependent (to), origin (from)]
            int from = prerequisite[1];
            int to = prerequisite[0];
            adjList.get(from).add(to);

            // increment inDegree[to] by 1
            inDegree.set(to, inDegree.get(to) + 1);
        }

        // 2. do Kahn's algorithm

        // 2a. initialize a queue
        Queue<Integer> queue = new LinkedList<>();
        // and add all nodes with inDegree 0 to it
        for(int i = 0; i < inDegree.size(); i++) {
            if(inDegree.get(i) == 0) queue.offer(i);
        }

        int nodesVisited = 0;
        while(!queue.isEmpty()) {
            // get node from front of Queue
            Integer n = queue.poll();
            // increment number of nodes visited
            nodesVisited++;

            // all dependents of n will now have inDegree reduced by 1
            for(Integer dependent: adjList.get(n)) {
                inDegree.set(dependent, inDegree.get(dependent) - 1);
                // if the new inDegree=0; add the node to the queue
                if (inDegree.get(dependent) == 0) {
                    queue.offer(dependent);
                }
            }
        }

        return nodesVisited == numCourses;
    }
}