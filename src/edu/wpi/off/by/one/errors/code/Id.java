package edu.wpi.off.by.one.errors.code;

public class Id {
	int indice;
	int unique;
	public Id(int i, int u){
		indice = i;
		unique = u;
	}
	public Id(){
		indice = -1;
		unique = -1;
	}

}
