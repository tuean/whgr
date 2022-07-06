package com.tuean.whgr;

public class ListNodeTest {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {val = x;}
    }

    static class Solution {
        public ListNode merge(ListNode l1, ListNode l2) {
            ListNode tmp = new ListNode(-1);
            ListNode res = tmp;
            while (l1 != null && l2 != null ) {
                if (l1.val <= l2.val) {
                    tmp.next = l1;
                    tmp = tmp.next;
                    l1 = l1.next;
                } else {
                    tmp.next = l2;
                    tmp = tmp.next;
                    l2 = l2.next;
                }
            }

            if (l1 != null) {
                tmp.next = l1;
            } else {
                tmp.next = l2;
            }
            return res.next;
        }
    }


    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(5);

        Solution s = new Solution();
        ListNode result = s.merge(l1, l2);
        System.out.println(result);
    }
}
