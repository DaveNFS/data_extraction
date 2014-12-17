	public class tagRule
	{
		public String phrase;
		public int Subjectivefrequency;
		public int NotSubjectivefrequency;
		
		public tagRule(String p, int Subj, int NSubj)
		{
			this.phrase = p;
			this.Subjectivefrequency = Subj;
			this.NotSubjectivefrequency = NSubj;
		}
	}