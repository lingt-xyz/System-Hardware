/**
 * Created by Ling Tan on 2017-03-15.
 * <p>
 * Detect all occurrences of a particular string (pattern) in a longer string (target).
 * The pattern is indexed from 0 to 'm', while the target is indexed from 0 to 'n'.
 * Indices correspond to assembly offsets.
 * <p>
 * 'i' is the current guess of a target index at which the pattern might begin.
 * 'j' indexes the pattern, and 'k' indexes the target.
 * We compare characters, one by one, as long as the characters match.
 * If they do and the pattern is exhausted, we have found a match.
 * However, if they do and the target is exhausted, we must be careful not to read irrelevant data past the target from memory.
 */
public class LoadStoreEmulator {

    private static void match(String pattern, String target) {
        char[] pattern_array = pattern.toCharArray();
        int m = pattern_array.length - 1;// highest pattern index
        char[] target_array = target.toCharArray();
        int n = target_array.length - 1;// highest target index

        int[] stack = new int[target_array.length];// match positions (indices)
        int top = -1;
        int top_reg;// indexes the stack
        int i_reg = 0;//the current guess of a target index at which the pattern might begin
        int j_reg = 0;//indexes the pattern
        int k_reg = 0;//indexes the target
        int m_reg = m;//load m
        int n_reg = n;//load n
        char x_reg;
        char y_reg;

        outer:
        while (i_reg != n_reg) {
            k_reg = i_reg;
            j_reg = 0;
            x_reg = pattern_array[j_reg];//load a char from pattern
            y_reg = target_array[k_reg];//load a char from target

            inner:
            while (x_reg == y_reg) {
                if (j_reg == m_reg) {//pattern exhausted, record match
                    top_reg = top;//load top
                    top_reg++;
                    stack[top_reg] = i_reg;//store stack
                    top = top_reg;//store top
                    break inner;
                }
                if (k_reg == n_reg) {//target exhausted
                    break outer;
                }
                x_reg = pattern_array[++j_reg];//load next char
                y_reg = target_array[++k_reg];//load next char
            }
            i_reg++;
        }

        if (top == -1) {// no matches were found
            System.out.print("The pattern was not found.");
        } else {// at least one match
            System.out.print("The pattern was found " + (top + 1) + " time(s), at position(s): ");
            for (int i = 0; i < top + 1; i++) {
                if (i != 0) {
                    System.out.print(", ");
                }
                System.out.print(stack[i]);
            }
        }
    }

    public static void main(String[] args) {
        String patter1 = "abc";
        String target1 = "aabcdababcxyabceabc";
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Case 1: Pattern = " + patter1 + ", target = " + target1 + ": ");
        match(patter1, target1);
        System.out.println("\n-------------------------------------------------------------------------");
        String patter2 = "aa";
        String target2 = "aaaaaaaaaa";
        System.out.println("Case 2: Pattern = " + patter2 + ", target = " + target2 + ": ");
        match(patter2, target2);
        System.out.println("\n-------------------------------------------------------------------------");
        String patter3 = "abe";
        String target3 = "aabcdababcxyabceabc";
        System.out.println("Case 3: Pattern = " + patter3 + ", target = " + target3 + ": ");
        match(patter3, target3);
        System.out.println("\n-------------------------------------------------------------------------");
        String patter4 = "abc";
        String target4 = "abc";
        System.out.println("Case 4: Pattern = " + patter4 + ", target = " + target4 + ": ");
        match(patter4, target4);
        System.out.println("\n-------------------------------------------------------------------------");
        String patter5 = "abcd";
        String target5 = "abc";
        System.out.println("Case 5: Pattern = " + patter5 + ", target = " + target5 + ": ");
        match(patter5, target5);
        System.out.println("\n-------------------------------------------------------------------------");


        /*
        *   Output:
        *   -------------------------------------------------------------------------
            Case 1: Pattern = abc, target = aabcdababcxyabceabc:
            The pattern was found 4 time(s), at position(s): 1, 7, 12, 16
            -------------------------------------------------------------------------
            Case 2: Pattern = aa, target = aaaaaaaaaa:
            The pattern was found 9 time(s), at position(s): 0, 1, 2, 3, 4, 5, 6, 7, 8
            -------------------------------------------------------------------------
            Case 3: Pattern = abe, target = aabcdababcxyabceabc:
            The pattern was not found.
            -------------------------------------------------------------------------
            Case 4: Pattern = abc, target = abc:
            The pattern was found 1 time(s), at position(s): 0
            -------------------------------------------------------------------------
            Case 5: Pattern = abcd, target = abc:
            The pattern was not found.
            -------------------------------------------------------------------------
        *
        * */
    }
}
