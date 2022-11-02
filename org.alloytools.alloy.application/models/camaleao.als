/*
Complete o seguinte modelo de uma colónia de camaleões onde o número de
camaleões é fixo mas onde a cor de cada camaleão pode mudar de acordo com
as seguintes regras:
- As cores possíveis são Verde, Azul e Amarelo
- Se 2 camaleões de cores diferentes se encontram mudam ambos para a terceira cor
- As cores só se alteram na situação acima
*/

sig Camaleao {
	var cor: one Cor
}

abstract sig Cor {}
one sig Azul,Verde,Amarelo extends Cor {}

pred nop {
	cor' = cor
}

pred encontro[x,y : Camaleao] {
	// guards
	x.cor != y.cor
	// efects
 	x.cor' = Cor - x.cor - y.cor
	y.cor' = x.cor'
	// frame conditions
	// cor' - (x+y) -> Cor =  cor - (x+y) -> Cor =
	// all c:Camaleao-x-y | c.cor' = c.cor
	(Camaleao-x-y)<:cor' = (Camaleao-x-y)<:cor
}

fact Comportamento {
}

// Especifique as seguintes propriedades desta colónia

assert Estabilidade {
	// Se os camaleoes ficarem todos da mesma cor, as cores nunca mais mudam
	always (one Camaleao.cor implies always cor'=cor)
}

check Estabilidade for 5

assert NaoConvergencia {
	// Se inicialmente há um camaleao verde e nenhum azul então não é possível
	// que a colónia fique toda amarela
	one cor.Verde and no cor.Azul implies always Camaleao.cor != Amarelo
}

check NaoConvergencia for 5

// Especifique um cenário onde existe um camaleao que não para de mudar de cor
// tomando recorrentemente todas as cores possíveis

run Exemplo {
//	some c:Camaleao | always {
//		eventually c.cor in Azul
//		eventually c.cor in Verde
//		eventually c.cor in Amarelo
//	}

	some c:Camaleao | all o : Cor | always eventually c.cor in o
}

