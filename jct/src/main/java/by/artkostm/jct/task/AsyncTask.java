package by.artkostm.jct.task;

import java.util.concurrent.Executor;

public abstract class AsyncTask<Input, Output, Progress>
{
    public abstract Input onPreExecute();
    
    public abstract Output doInBackground(Input t);
    
    public abstract void onPostExecute(Output v);
    
    public void onProgressUpdate(Progress...p)
    {
        
    }
    
    public final void publishProgress(Progress...p)
    {
        onProgressUpdate(p);
    }
    
    public AsyncTask()
    {
    }

    public final void execute()
    {
        CallbackExecutor executor = new CallbackExecutor();
        
        executor.execute(new CallbackRunnable()
        {
            Input t = null;
            Output v = null;
            @Override
            public void run()
            {
                v = doInBackground(t);
            }
            
            @Override
            public void postCalback()
            {
                onPostExecute(v);
            }

            @Override
            public void preCalback()
            {
                t = onPreExecute();
            }
        });
        
    }
    
    public static interface CallbackRunnable extends Runnable
    {
        public void preCalback();
        public void postCalback();
    }
    
    public static class CallbackExecutor implements Executor
    {

        @Override
        public void execute(final Runnable command)
        {
            final Thread runner = new Thread(command);
            
            System.out.println("executor[ thread: "+Thread.currentThread().getName()+" ]");
            
            if(command instanceof CallbackRunnable)
            {
                Thread callerbacker = new Thread( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            ((CallbackRunnable)command).preCalback();
                            runner.start();
                            runner.join();
                            ((CallbackRunnable)command).postCalback();
                        }
                        catch(InterruptedException e)
                        {
                        }
                    }
                });
                callerbacker.start();
            }
        }
        
    }
}
