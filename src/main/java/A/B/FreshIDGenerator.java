package A.B;

public class FreshIDGenerator {
    
    public static FreshIDGenerator idGenerator = new FreshIDGenerator() ;
       
    private int nextIdToGenerate = 0 ;

    private FreshIDGenerator() { }
    
    public int getNextId() {
        int id = nextIdToGenerate ;
        nextIdToGenerate++ ;
        return id ;
    }

}
