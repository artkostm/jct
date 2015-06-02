package by.artkostm.jct;

import by.artkostm.jct.task.AsyncTask;


public class Sample
{
	public static void main(String[] args) throws InterruptedException
	{
	    LogTask lt = new LogTask();
	    lt.execute();
	    System.out.println("Hello!");
	    for(int i = 0; i < 14; i++){
	        Thread.sleep(600);
	        System.out.println("Step"+i);
	    }
	}
	
	private static class LogTask extends AsyncTask<String, String, Integer>
	{

        @Override
        public String onPreExecute()
        {
            String str = "Hello, World!";
            System.out.println("onPreExecute[ msg: "+str+", thread: "+Thread.currentThread().getName()+" ]");
            return str;
        }

        @Override
        public String doInBackground(String t)
        {
            for(int i = 0; i < 7; i++)
            {
                try{Thread.sleep(1000);}catch (InterruptedException e){}
                publishProgress((int)(i * 100 / (float) 7));
            }
            
            System.out.println("doInBackground[ msg: "+t+", thread: "+Thread.currentThread().getName()+" ]");
            t = t + 3;
            return t;
        }

        @Override
        public void onPostExecute(String v)
        {
            System.out.println("onPostExecute[ msg: "+v+", thread: "+Thread.currentThread().getName()+" ]");
        }
        
        @Override
        public void onProgressUpdate(Integer... p)
        {
            System.out.println("onProgressUpdate[ progress: "+p[0]+", thread: "+Thread.currentThread().getName()+" ]");
        }
	    
	}
}