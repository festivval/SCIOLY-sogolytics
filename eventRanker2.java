import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class eventRanker2 {

    final ArrayList<String> events; // list of event names
    final ArrayList<ArrayList<String>> rankings; // list of numbered event rankings from SOGOLYTICS

    boolean hasNames, hasGrades; // from checkboxes

    public eventRanker2(ArrayList<String> ranks, boolean hasNames, boolean hasGrades)
    {
        this.hasNames = hasNames;
        this.hasGrades = hasGrades;

        // set event names
        events = new ArrayList<>(Arrays.asList(ranks.get(0).split("\t")));

        // set each member's rankings for each event ( + name & grade if included)
        rankings = new ArrayList<>();
        for( String s : ranks.subList(1, ranks.size()) )
            rankings.add(new ArrayList<>(Arrays.asList(s.split("\t"))));

    }

    /**
     * Converts event preferences from survey to display 1st, 2nd, 3rd, etc... choice of event
     * rather than displaying 5, 2, 9, 4, .... for alphabetically-ordered event ranking
     */
    public ArrayList<ArrayList<String>> returnTopChoices()
    {
        int i;
        ArrayList<ArrayList<String>> rankedEvents = new ArrayList<>();

        // add header
        ArrayList<String> header = new ArrayList<>();
        if(hasNames) {
            header.add("First Name");
            header.add("Last Name");
        }
        if(hasGrades)
            header.add("Grade");
        for(i = 1; i <= events.size(); i++) // priority #s
            header.add(String.valueOf(i));
        rankedEvents.add(header);

        for (ArrayList<String> ranking : rankings) {
            ArrayList<String> sorted = new ArrayList<>();

            Iterator<String> rankIter = ranking.iterator();
            Iterator<String> eventIter = events.iterator();

            // add names & grades to beginning of row, if included in input data
            if(hasNames) {
                sorted.add(rankIter.next());
                sorted.add(rankIter.next());
            }
            if(hasGrades)
                sorted.add(rankIter.next());

            // read & store event ranking data for 1 person
            Tuple<String, Integer>[] cats = new Tuple[events.size()];
            for (i = 0; i < events.size(); i++)
                cats[i] = new Tuple<>(eventIter.next(), Integer.parseInt(rankIter.next()));

            // sort events from highest to lowest priority
            QuickSort.sort(cats, 0, events.size() - 1);

            // add ranking data with event names
            for (Tuple<String, Integer> t : cats)
                sorted.add(t.x);

            // add this member's sorted event ranks to final list of member event ranks
            rankedEvents.add(sorted);
        }

        // System.out.println("Finished sorting");
        return rankedEvents;
    }

}
