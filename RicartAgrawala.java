import java.io.*;

public class RicartAgrawala{

	public boolean bRequestingCS;
	public int outstandingReplies;
	public int highestSeqNum;
	public int seqNum;
	public int nodeNum;
	public Driver driverModule;
	
	//Holds our writers to use
	public PrintWriter[] w;
	
	//Hard coded to 3 right now, for 3 other nodes in network
	public int channelCount = 3;
	
	public boolean[] replyDeferred;
	
	public RicartAgrawala(int nodeNum, int seqNum, Driver driverModule)
    {
		bRequestingCS = false;
		outstandingReplies = channelCount;
		highestSeqNum = 0;
		this.seqNum = seqNum;
		this.driverModule = driverModule;
		w = new PrintWriter[channelCount];
		// Número de nodo también se utiliza para la prioridad (bajo nodo # == mayor prioridad en el esquema RicartAgrawala)
		// Números de nodo son [1, channelCount], ya que estamos empezando en 1 cheque por errores que intentan acceder nodo '0 '.
		this.nodeNum = nodeNum;
		replyDeferred = new boolean[channelCount];
	}
	
	/**  Invocación (iniciado en el módulo controlador con la petición CS) */
	public boolean invocation()
	{
		
		bRequestingCS = true;
		seqNum = highestSeqNum + 1;
		
		outstandingReplies = channelCount;
		
		for(int i = 1; i <= channelCount + 1; i++){
			if(i != nodeNum){
				requestTo(seqNum, nodeNum, i);
			}
		}
		
		
		while(outstandingReplies > 0)
		{
			try{
				Thread.sleep(1000);
				
			}
			catch(Exception e){
				
			}
			/* esperar hasta que tengamos respuestas de todos los demás procesos */
		}

		// Volvemos cuando esté listo para entrar en CS
		return true;
		
		
	}
	
	// La otra mitad de la invocación
	public void releaseCS()
	{
		bRequestingCS = false;
		
		for(int i = 0; i < channelCount; i++){
			if(replyDeferred[i]){
				replyDeferred[i] = false;
				if(i < (nodeNum - 1))
					replyTo(i + 1);
				else
					replyTo(i + 2);
			}
		}
	}
	
	/** Recepción de solicitud
        *
        * @ Param j número de secuencia del mensaje entrante
        * @ Param k el número de nodo del mensaje entrante
	 * 
	 */
	public void receiveRequest(int j, int k){
		System.out.println("Solicitud recibida desde el nodo " + k);
		boolean bDefer = false;
		
		highestSeqNum = Math.max(highestSeqNum, j);
		bDefer = bRequestingCS && ((j > seqNum) || (j == seqNum && k > nodeNum));
		if(bDefer){
			System.out.println("Diferido de envío de mensajes a " + k);
			if(k > nodeNum)
				replyDeferred[k - 2] = true;
			else
				replyDeferred[k - 1] = true;
		}
		else{ 
			System.out.println("Enviado mensaje de respuesta " + k);
			replyTo(k);
		}
		
	}
	
	/** Recibir respuestas */
	public void receiveReply(){
		outstandingReplies = Math.max((outstandingReplies - 1), 0);
		//System.out.println("Outstanding replies: " + outstandingReplies);
	}
	
	public void replyTo(int k)
	{
		System.out.println("Enviar la respuesta al nodo " + k);
		if(k > nodeNum)
		{
			w[k-2].println("RESPUESTA," + k);
		}
		else
		{
			w[k-1].println("RESPUESTA," + k);
		}
	}
	
	public void requestTo(int seqNum, int nodeNum, int i)
	{
		System.out.println("Enviar la solicitud al nodo " + (((i))));
		if(i > nodeNum)
		{
			w[i-2].println("SOLICITUD," + seqNum + "," + nodeNum);
		}
		else
		{
			w[i-1].println("SOLICITUD," + seqNum + "," + nodeNum);
		}
	}

}
