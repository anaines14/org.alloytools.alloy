sig File {
  	
	link : set File
}

sig Trash in File {}
sig Protected in File {}

/* The trash is empty. */
pred inv1 {
	historically (once (no Trash & Protected)) 
}