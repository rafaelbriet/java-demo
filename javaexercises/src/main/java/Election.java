public class Election {
    public static void main(String[] args) {
        int totalVoters = 1000;
        int validsVotes = 800;
        int blankVotes = 150;
        int nullVotes = 50;
        System.out.println("Valid Vote Percentage: " + calculateValidVotePercentage(totalVoters, validsVotes) + "%");
        System.out.println("Blank Vote Percentage: " + calculateBlankVotePercentage(totalVoters, blankVotes) + "%");
        System.out.println("Null Vote Percentage: " + calculateNullVotePercentage(totalVoters, nullVotes) + "%");
        System.out.println("Total Votes: " + (validsVotes + blankVotes + nullVotes));
    }

    public static int calculateValidVotePercentage(int totalVoters, int validVotes) {
        if (totalVoters == 0) {
            return 0;
        }
        return (validVotes * 100) / totalVoters;
    }

    public static int calculateBlankVotePercentage(int totalVoters, int blankVotes) {
        if (totalVoters == 0) {
            return 0;
        }
        return (blankVotes * 100) / totalVoters;
    }

    public static int calculateNullVotePercentage(int totalVoters, int nullVotes) {
        if (totalVoters == 0) {
            return 0;
        }
        return (nullVotes * 100) / totalVoters;
    }
}
