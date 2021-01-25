
from edu.cmu.cs.stage3.alice.authoringtool import JAlice
from edu.cmu.cs.stage3.util import StringTypePair
from java.lang import Boolean
from java.lang import Double
from java.lang import Integer
from java.lang import String
from edu.cmu.cs.stage3.math import Vector3
from edu.cmu.cs.stage3.math import Matrix44
import edu
import java
import javax
import string

# HACK: until os.path works
def os_path_join( *args ):
	return string.join( args, java.io.File.separator )

####################################
# load common resource data
####################################

standardResourcesFile = java.io.File( JAlice.getAliceHomeDirectory(), "resources/common/StandardResources.py" )
execfile( standardResourcesFile.getAbsolutePath() )


##################
# Format Config
##################

formatMap = {
	edu.cmu.cs.stage3.alice.core.response.MoveAnimation : "<<<subject>>> mova <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.MoveTowardAnimation : "<<<subject>>> mova <<amount>> para <<target>>",
	edu.cmu.cs.stage3.alice.core.response.MoveAwayFromAnimation : "<<<subject>>> mova <<amount>> a partir de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAnimation : "<<<subject>>> gire <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.RollAnimation : "<<<subject>>> role <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.MoveAtSpeed : "<<<subject>>> mova na velocidade <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAtSpeed : "<<<subject>>> gire na velocidade <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.RollAtSpeed : "<<<subject>>> role na velocidade <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.ResizeAnimation : "<<<subject>>> redimensione <<amount>>",
	edu.cmu.cs.stage3.alice.core.response.PointAtAnimation : "<<<subject>>> aponte para <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnToFaceAnimation : "<<<subject>>> gire para a face <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAwayFromAnimation : "<<<subject>>> gire se afastando de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.PointAtConstraint : "<<<subject>>> restringido para apontar <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnToFaceConstraint : "<<<subject>>> restringido para olhar de frente <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAwayFromConstraint : "<<<subject>>> restringido a virar para <<target>>",
	edu.cmu.cs.stage3.alice.core.response.GetAGoodLookAtAnimation : "<<<subject>>> obtenha uma boa vis�o de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.StandUpAnimation : "<<<subject>>> levante",
	edu.cmu.cs.stage3.alice.core.response.PositionAnimation : "<<<subject>>> mova para <<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PlaceAnimation : "<<<subject>>> caitlin move para <<amount>><<spatialRelation>><<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation : "<<<subject>>> oriente para <<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation : "<<<subject>>> defina ponto de vista para <<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PropertyAnimation : "<<<element>>> defina <propertyName> para <<value>>",
	edu.cmu.cs.stage3.alice.core.response.SoundResponse : "<<<subject>>> reproduza o som <<sound>>",
	edu.cmu.cs.stage3.alice.core.response.Wait : "Espere <<duration>>",
	edu.cmu.cs.stage3.alice.core.response.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.response.Print : "mostre <<text>> <<object>>",
	edu.cmu.cs.stage3.alice.core.response.CallToUserDefinedResponse : "<userDefinedResponse><requiredActualParameters>",
	edu.cmu.cs.stage3.alice.core.response.ScriptResponse : "Roteiro <<script>>",
	edu.cmu.cs.stage3.alice.core.response.ScriptDefinedResponse : "Resposta Definida por Roteiro <<script>>",
	edu.cmu.cs.stage3.alice.core.response.SayAnimation : "<<<subject>>> diga <<what>>",
	edu.cmu.cs.stage3.alice.core.response.ThinkAnimation : "<<<subject>>> pense <<what>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.PositionKeyframeResponse : "anima��o de quadro chave por posi��o <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.QuaternionKeyframeResponse : "anima��o de quadro chave por orienta��o <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.ScaleKeyframeResponse : "anima��o de quadro chave por escala <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.KeyframeResponse : "anima��o de quadro chave <<subject>>",
	edu.cmu.cs.stage3.alice.core.response.PoseAnimation : "<<<subject>>> defina pose <<pose>>",
	edu.cmu.cs.stage3.alice.core.response.Increment : "incremente <<<variable>>> por 1",
	edu.cmu.cs.stage3.alice.core.response.Decrement : "decremente <<<variable>>> por 1",

	edu.cmu.cs.stage3.alice.core.response.VehiclePropertyAnimation : "<element> defina <propertyName> para <value>",

	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtBeginning : "adicione <item> no in�cio de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtEnd : "adicione <item> no final de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtIndex : "adicione <item> na posi��o <index> de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromBeginning : "adicione item do in�cio de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromEnd : "remova item do final de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromIndex : "remova item da posi��o <index> de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.Clear : "remova todos itens de <<<list>>>",

	edu.cmu.cs.stage3.alice.core.response.array.SetItemAtIndex : "defina item <index> para <item> em <<<array>>>",

	edu.cmu.cs.stage3.alice.core.response.vector3.SetX : "defina dist�ncia de <<<vector3>>> � direita <<value>>",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetY : "defina dist�ncia de <<<vector3>>> acima <<value>>",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetZ : "defina dist�ncia de <<<vector3>>> a frente <<value>>",

	edu.cmu.cs.stage3.alice.core.question.userdefined.CallToUserDefinedQuestion : "<userDefinedQuestion><requiredActualParameters>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Return : "Devolva <<value>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Print : "mostre <<text>> <<object>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.PropertyAssignment : "<element> defina <propertyName> para <value>",

	edu.cmu.cs.stage3.alice.core.question.PartKeyed : "Parte de <<<owner>>> nomeada <key>",

	edu.cmu.cs.stage3.alice.core.question.Width : "Largura de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Height : "Altura de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Depth : "Profundidade de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Quaternion : "Quaternion <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Position : "Posi��o de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.PointOfView : "Ponto de vista <<<subject>>>",

	edu.cmu.cs.stage3.alice.core.question.Not : "N�o <a>",
	edu.cmu.cs.stage3.alice.core.question.And : "ambos <a> e <b>",
	edu.cmu.cs.stage3.alice.core.question.Or : "qualquer <a> ou <b>, ou ambos",

	edu.cmu.cs.stage3.alice.core.question.StringConcatQuestion : "<a> junto com <b>",
	edu.cmu.cs.stage3.alice.core.question.ToStringQuestion : "<what> como uma cadeia de caracteres",

	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForNumber : "pe�a ao usu�rio um n�mero <<question>>",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserYesNo : "pe�a ao usu�rio sim ou n�o <<question>>",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForString : "pe�a ao usu�rio uma cadeia de caracteres <<question>>",

	edu.cmu.cs.stage3.alice.core.question.IsEqualTo : "<a>==<b>",
	edu.cmu.cs.stage3.alice.core.question.IsNotEqualTo : "<a>!=<b>",

	edu.cmu.cs.stage3.alice.core.question.NumberIsEqualTo : "<a>==<b>",
	edu.cmu.cs.stage3.alice.core.question.NumberIsNotEqualTo : "<a>!=<b>",
	edu.cmu.cs.stage3.alice.core.question.NumberIsGreaterThan : "<a>><b>",
	edu.cmu.cs.stage3.alice.core.question.NumberIsGreaterThanOrEqualTo : "<a>>=<b>",
	edu.cmu.cs.stage3.alice.core.question.NumberIsLessThan : "<a>&lt;<b>",
	edu.cmu.cs.stage3.alice.core.question.NumberIsLessThanOrEqualTo : "<a>&lt;=<b>",

	edu.cmu.cs.stage3.alice.core.question.NumberAddition : "(<a>+<b>)", 
	edu.cmu.cs.stage3.alice.core.question.NumberSubtraction : "(<a>-<b>)", 
	edu.cmu.cs.stage3.alice.core.question.NumberMultiplication : "(<a>*<b>)", 
	edu.cmu.cs.stage3.alice.core.question.NumberDivision : "(<a>/<b>)",

	edu.cmu.cs.stage3.alice.core.question.math.Min : "m�nimo de <a> e <b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Max : "m�ximo de <a> e <b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Abs : "valor absoluto de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Sqrt : "raiz quadrada de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Floor : "maior inteiro n�o maior que <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Ceil : "o menor inteiro n�o menor que <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Sin : "sen <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Cos : "cos <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Tan : "tan <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ASin : "arcsen <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ACos : "arccos <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ATan : "arctan <a>",
	edu.cmu.cs.stage3.alice.core.question.math.ATan2 : "arctan2 <a><b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Pow : "<a> elevado a <b> pot�ncia", 
	edu.cmu.cs.stage3.alice.core.question.math.Log : "log natural de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Exp : "e elevado a <a> pot�ncia", 
	edu.cmu.cs.stage3.alice.core.question.math.IEEERemainder : "Resto resultante de <a>/<b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Int : "int <a>",
	edu.cmu.cs.stage3.alice.core.question.math.Round : "arredondado <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ToDegrees : "<a> convertido de radianos para graus", 
	edu.cmu.cs.stage3.alice.core.question.math.ToRadians : "<a> convertido de graus para radianos", 
	edu.cmu.cs.stage3.alice.core.question.math.SuperSqrt : "a <b>a. raiz de <a>",

	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromLeftEdge : "dist�ncia do mouse at� a margem esquerda", 
	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromTopEdge : "dist�ncia do mouse at� a margem superior", 

	edu.cmu.cs.stage3.alice.core.question.time.TimeElapsedSinceWorldStart : "tempo decorrido", 

	edu.cmu.cs.stage3.alice.core.question.time.Year : "ano", 
	edu.cmu.cs.stage3.alice.core.question.time.MonthOfYear : "m�s do ano", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfYear : "dia do ano", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfMonth : "dia do m�s", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeek : "dia da semana", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeekInMonth : "dia da semana no m�s", 
	edu.cmu.cs.stage3.alice.core.question.time.IsAM : "� AM", 
	edu.cmu.cs.stage3.alice.core.question.time.IsPM : "� PM", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfAMOrPM : "hora de AM ou PM", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfDay : "hora do dia", 
	edu.cmu.cs.stage3.alice.core.question.time.MinuteOfHour : "minuto da hora", 
	edu.cmu.cs.stage3.alice.core.question.time.SecondOfMinute : "segundo do minuto", 

	edu.cmu.cs.stage3.alice.core.question.RandomBoolean : "escolha verdadeira <probabilityOfTrue> do tempo",
	edu.cmu.cs.stage3.alice.core.question.RandomNumber : "n�mero rand�mico",

	edu.cmu.cs.stage3.alice.core.question.list.Contains : "<list> cont�m <item>",
	edu.cmu.cs.stage3.alice.core.question.list.FirstIndexOfItem : "primeiro �ndice do <item> de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.IsEmpty : "<list> est� vazia",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtBeginning : "primeiro item de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtEnd : "�ltimo item de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtIndex : "item <index> de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtRandomIndex : "item rand�mico de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.LastIndexOfItem : "�ltimo �ndice de <item> de <list>",
	edu.cmu.cs.stage3.alice.core.question.list.Size : "tamanho de <list>",

	edu.cmu.cs.stage3.alice.core.question.array.ItemAtIndex : "item <index> de <<<array>>>",
	edu.cmu.cs.stage3.alice.core.question.array.Size : "tamanho de <<<array>>>",

	edu.cmu.cs.stage3.alice.core.question.IsAbove : "<<<subject>>> est� acima <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsBehind : "<<<subject>>> est� atr�s <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsBelow : "<<<subject>>> est� abaixo <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsInFrontOf : "<<<subject>>> est� em frente de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsLeftOf : "<<<subject>>> est� � esquerda de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsRightOf : "<<<subject>>> est� � direita de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsSmallerThan : "<<<subject>>> � menor que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsLargerThan : "<<<subject>>> � maior que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsNarrowerThan : "<<<subject>>> � mais estreito que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsWiderThan : "<<<subject>>> � mais amplo que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsShorterThan : "<<<subject>>> � mais curto que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsTallerThan : "<<<subject>>> � mais alto que <<object>>",
 
	edu.cmu.cs.stage3.alice.core.question.IsCloseTo : "<<<subject>>> est� dentro <threshold> de <object>",
	edu.cmu.cs.stage3.alice.core.question.IsFarFrom : "<<<subject>>> est� pelo menos <threshold> longe de <object>",
	edu.cmu.cs.stage3.alice.core.question.DistanceTo : "<<<subject>>> dist�ncia para <<object>>",

	edu.cmu.cs.stage3.alice.core.question.DistanceToTheLeftOf : "<<<subject>>> dist�ncia a esquerda de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceToTheRightOf : "<<<subject>>> dist�ncia a direita de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceAbove : "<<<subject>>> dist�ncia acima de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceBelow : "<<<subject>>> dist�ncia abaixo de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceInFrontOf : "<<<subject>>> dist�ncia na frente de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceBehind : "<<<subject>>> dist�ncia atr�s <<object>>",

	edu.cmu.cs.stage3.alice.core.question.vector3.X : "dist�ncia direita de <<<vector3>>>",
	edu.cmu.cs.stage3.alice.core.question.vector3.Y : "dist�ncia acima de <<<vector3>>>",
	edu.cmu.cs.stage3.alice.core.question.vector3.Z : "dist�ncia a frente de <<<vector3>>>",

	edu.cmu.cs.stage3.alice.core.question.PickQuestion : "objeto sob o cursor do mouse",

	edu.cmu.cs.stage3.alice.core.question.RightUpForward : "<right>, <up>, <forward>",

	edu.cmu.cs.stage3.alice.core.question.Pose : "Pose atual de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.PropertyValue : "<<<element>>>.<propertyName>",

	edu.cmu.cs.stage3.alice.core.question.visualization.model.Item : "o valor de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.model.SetItem : "deixe <<<subject>>> = <item>",

	edu.cmu.cs.stage3.alice.core.question.visualization.array.ItemAtIndex : "o valor de <<<subject>>>[ <index> ]",
	edu.cmu.cs.stage3.alice.core.response.visualization.array.SetItemAtIndex : "deixe <<<subject>>>[ <index> ] = <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.array.Size : "tamanho do <<<subject>>>",

	edu.cmu.cs.stage3.alice.core.question.visualization.list.Size : "tamanho do <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.Contains : "<<<subject>>> cont�m <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.IsEmpty : "<<<subject>>> est� vazio",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.FirstIndexOfItem : "<<<subject>>> � o primeiro �ndice do <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.LastIndexOfItem : "<<<subject>>> � o �ltimo �ndice do <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtBeginning : "<<<subject>>> � o item no in�cio",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtEnd : "<<<subject>>> � o item no final",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtIndex : "<<<subject>>> � o item no �ndice <index>",
	
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtBeginning : "adicione <item> no in�cio de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtEnd : "adicione <item> no final de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtIndex : "adicione <item> no <index> de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromBeginning : "remova um item do in�cio de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromEnd : "remova um item do final de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromIndex : "remova um item do <index> de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.Clear : "limpe <<<subject>>>",
}



##################
# Name Config
##################

nameMap = {
	"edu.cmu.cs.stage3.alice.core.response.DoInOrder" : "Fa�a em ordem",
	"edu.cmu.cs.stage3.alice.core.response.DoTogether" : "Fa�a junto",
	"edu.cmu.cs.stage3.alice.core.response.IfElseInOrder" : "Se/Sen�o",
	"edu.cmu.cs.stage3.alice.core.response.LoopNInOrder" : "Repita",
	"edu.cmu.cs.stage3.alice.core.response.WhileLoopInOrder" : "Enquanto",
	"edu.cmu.cs.stage3.alice.core.response.ForEachInOrder" : "Para todos em ordem",
	"edu.cmu.cs.stage3.alice.core.response.ForEachTogether" : "Para todos juntos",
	"edu.cmu.cs.stage3.alice.core.response.Print" : "Mostre",
	"edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation.quaternion" : "deslocamento por",
	"edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation.pointOfView" : "ponto de vista de",
	"edu.cmu.cs.stage3.alice.core.response.PositionAnimation.position" : "deslocamento por",

	"edu.cmu.cs.stage3.alice.core.question.userdefined.Return" : "Devolve",

	"edu.cmu.cs.stage3.alice.core.behavior.WorldStartBehavior" : "Quando o mundo come�a",
	"edu.cmu.cs.stage3.alice.core.behavior.WorldIsRunningBehavior" : "Enquanto o mundo est� executando",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyClickBehavior" : "Quando <keyCode> � digitado",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyIsPressedBehavior" : "Enquanto <keyCode> � pressionado",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonClickBehavior" : "Quando <mouse> � clicado sobre <onWhat>",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonIsPressedBehavior" : "Enquanto <mouse> � pressionado sobre <onWhat>",
	"edu.cmu.cs.stage3.alice.core.behavior.ConditionalBehavior" : "Enquanto <condition> � verdadeira",
	"edu.cmu.cs.stage3.alice.core.behavior.ConditionalTriggerBehavior" : "Quando <condition> se torna verdadeira",
	"edu.cmu.cs.stage3.alice.core.behavior.VariableChangeBehavior" : "Quando <variable> muda",
	"edu.cmu.cs.stage3.alice.core.behavior.MessageReceivedBehavior" : "Quando uma mensagem � recebida por <toWhom> de <fromWho>", 
	"edu.cmu.cs.stage3.alice.core.behavior.DefaultMouseInteractionBehavior" : "Deixe <mouse> mover <objects>",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyboardNavigationBehavior" : "Deixe <arrowKeys> mover <subject>",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseNavigationBehavior" : "Deixe <mouse> mover the camera",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseLookingBehavior" : "Deixe <mouse> orientar the camera",
	"edu.cmu.cs.stage3.alice.core.behavior.SoundMarkerPassedBehavior" : "Quando som marcado <marker> � reproduzido",
	"edu.cmu.cs.stage3.alice.core.behavior.SoundLevelBehavior" : "Quando o n�vel de grava��o de som � >= <level>",

	"edu.cmu.cs.stage3.alice.core.Model.opacity" : "opacidade",
	"edu.cmu.cs.stage3.alice.core.Model.diffuseColorMap" : "textura da pele",
	"diffuseColorMap" : "textura da pele",
	"edu.cmu.cs.stage3.alice.core.Transformable.localTransformation" : "pontoDeVista",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonClickBehavior.onWhat" : "emQue",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonIsPressedBehavior.onWhat" : "emQue",
	"edu.cmu.cs.stage3.alice.core.question.IsCloseTo.threshold" : "est� dentro",
	"edu.cmu.cs.stage3.alice.core.question.IsFarFrom.threshold" : "est� pelo menos",
	"edu.cmu.cs.stage3.alice.core.question.IsCloseTo.object" : "de",
	"edu.cmu.cs.stage3.alice.core.question.IsFarFrom.object" : "dist�ncia a partir de",

	"edu.cmu.cs.stage3.alice.scenegraph.renderer.directx7renderer.Renderer" : "DirectX 7",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.openglrenderer.Renderer" : "OpenGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.java3drenderer.Renderer" : "Java3D",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.joglrenderer.Renderer" : "JOGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.nullrenderer.Renderer" : "None",

	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_GENTLY : "suavemente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_GENTLY_AND_END_ABRUPTLY : "inicia suavemente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_ABRUPTLY_AND_END_GENTLY : "finaliza suavemente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_ABRUPTLY : "bruscamente",

	edu.cmu.cs.stage3.alice.core.Direction.LEFT : "esquerda",
	edu.cmu.cs.stage3.alice.core.Direction.RIGHT : "direita",
	edu.cmu.cs.stage3.alice.core.Direction.UP : "cima",
	edu.cmu.cs.stage3.alice.core.Direction.DOWN : "baixo",
	edu.cmu.cs.stage3.alice.core.Direction.FORWARD : "frente",
	edu.cmu.cs.stage3.alice.core.Direction.BACKWARD : "atr�s",

	edu.cmu.cs.stage3.alice.core.SpatialRelation.LEFT_OF : "esquerda de",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.RIGHT_OF : "direita de ",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.ABOVE : "acima",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BELOW : "abaixo",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.IN_FRONT_OF : "em frente de",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BEHIND : "atr�s",

	edu.cmu.cs.stage3.alice.core.Dimension.ALL : "tudo",
	edu.cmu.cs.stage3.alice.core.Dimension.LEFT_TO_RIGHT : "esquerda para direita",
	edu.cmu.cs.stage3.alice.core.Dimension.TOP_TO_BOTTOM : "topo para base",
	edu.cmu.cs.stage3.alice.core.Dimension.FRONT_TO_BACK : "frente para atr�s",

	edu.cmu.cs.stage3.alice.core.FogStyle.NONE : "nenhum fog",
	edu.cmu.cs.stage3.alice.core.FogStyle.LINEAR : "dist�ncia",
	edu.cmu.cs.stage3.alice.core.FogStyle.EXPONENTIAL : "densidade",

	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.SOLID : "s�lido",
 	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.WIREFRAME : "representa��o aramada",
	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.POINTS : "pontos",

	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.NONE : "nenhum",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.FLAT : "plano",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.SMOOTH : "liso",

	java.lang.Boolean : "Booleano",
	java.lang.Number : "N�mero",
	edu.cmu.cs.stage3.alice.core.Model : "Objeto",

	Boolean.TRUE : "verdadeiro",
	Boolean.FALSE : "falso",

	edu.cmu.cs.stage3.alice.scenegraph.Color.WHITE : "branco",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLACK : "preto",
	edu.cmu.cs.stage3.alice.scenegraph.Color.RED : "vermelho",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GREEN : "verde",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLUE : "azul",
	edu.cmu.cs.stage3.alice.scenegraph.Color.YELLOW : "amarelo",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PURPLE : "roxo",
	edu.cmu.cs.stage3.alice.scenegraph.Color.ORANGE : "laranja",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PINK : "rosa",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BROWN : "marrom",
	edu.cmu.cs.stage3.alice.scenegraph.Color.CYAN : "ciano",
	edu.cmu.cs.stage3.alice.scenegraph.Color.MAGENTA : "magenta",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GRAY : "cinza",
	edu.cmu.cs.stage3.alice.scenegraph.Color.LIGHT_GRAY : "cinza claro",
	edu.cmu.cs.stage3.alice.scenegraph.Color.DARK_GRAY : "cinza escuro",

	edu.cmu.cs.stage3.util.HowMuch.INSTANCE : "somento objeto",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_PARTS : "objeto e parte",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_ALL_DESCENDANTS : "objeto e todos descendentes",
}

htmlNameMap = {
	"edu.cmu.cs.stage3.alice.core.Transformable" : "[Obj]",
	"edu.cmu.cs.stage3.alice.core.Model" : "[Obj]",
	"java.lang.Number" : "[123]",
	"java.lang.Boolean" : "[T/F]",
	"java.lang.String" : "[ABC]",
	"edu.cmu.cs.stage3.alice.scenegraph.Color" : "[Cor]",
	"edu.cmu.cs.stage3.alice.core.TextureMap" : "[Textura]",
	"edu.cmu.cs.stage3.alice.core.Sound" : "[Som]",
	"edu.cmu.cs.stage3.alice.core.Pose" : "[Pose]",
	"edu.cmu.cs.stage3.math.Vector3" : "[Pos]",
	"edu.cmu.cs.stage3.math.Quaternion" : "[Ori]",
	"edu.cmu.cs.stage3.math.Matrix44" : "[POV]",
	"edu.cmu.cs.stage3.alice.core.ReferenceFrame" : "[Obj]",
	"edu.cmu.cs.stage3.alice.core.Light" : "[Luz]",
	"edu.cmu.cs.stage3.alice.core.Direction" : "[Dire��o]",
	"edu.cmu.cs.stage3.alice.core.Collection" : "]]]",
}


####################
# Color Config
####################

colorMap = {
	"disabledHTMLText": java.awt.Color( 200, 200, 200 ),
	"disabledHTML": java.awt.Color( 230, 230, 230 ),
	"DoInOrder" : java.awt.Color( 255, 255, 210 ),
	"DoTogether" : java.awt.Color( 238, 221, 255 ),
	"IfElseInOrder" : java.awt.Color( 204, 238, 221 ),
	"LoopNInOrder" : java.awt.Color( 221, 249, 249 ),
	"WhileLoopInOrder" : java.awt.Color( 204, 255, 221 ),
	"ForEach" : java.awt.Color( 255, 230, 230 ),
	"ForEachInOrder" : java.awt.Color( 255, 230, 230 ),
	"ForAllTogether" : java.awt.Color( 248, 221, 255 ),
	"Wait" : java.awt.Color( 255, 230, 180 ),
	"ScriptResponse" : java.awt.Color( 255, 230, 180 ),
	"ScriptDefinedResponse" : java.awt.Color( 255, 230, 180 ),
	"Print" : java.awt.Color( 255, 230, 180 ),
	"Comment" : java.awt.Color( 255, 255, 255 ),
	"Return" : java.awt.Color( 212, 204, 249 ),
	"PropertyAssignment" : java.awt.Color( 255, 230, 180 ),
	"accessibleMathTile" : java.awt.Color( 255, 230, 180 ),
	"dndHighlight" : java.awt.Color( 255, 255, 0 ),
	"dndHighlight2" : java.awt.Color( 0, 200, 0 ),
	"dndHighlight3" : java.awt.Color( 230, 0, 0 ),
	"propertyViewControllerBackground" : java.awt.Color( 240, 240, 255 ),
	"objectTreeSelected" : java.awt.Color( 96, 32, 200 ),
	"objectTreeBackground" : java.awt.Color( 240, 233, 207 ),
	"objectTreeDisabled" : java.awt.Color( 220, 220, 220 ),
	"objectTreeText" : java.awt.Color( 0, 0, 0 ),
	"objectTreeDisabledText" : java.awt.Color( 150, 150, 150 ),
	"objectTreeSelectedText" : java.awt.Color( 240, 240, 240 ),
	"guiEffectsDisabledBackground" : java.awt.Color( 245, 245, 245, 100 ),
	"guiEffectsDisabledLine" : java.awt.Color( 128, 128, 128, 120 ),
	"guiEffectsShadow" : java.awt.Color( 0, 0, 0, 96 ),
	"guiEffectsEdge" : java.awt.Color( 0, 0, 0, 0 ),
	"guiEffectsTroughHighlight" : java.awt.Color( 255, 255, 255 ),
	"guiEffectsTroughShadow" : java.awt.Color( 96, 96, 96 ),
	"propertyDnDPanel" : java.awt.Color( 255, 255, 200 ),
	"prototypeParameter" : java.awt.Color( 255, 255, 200 ),
	"elementDnDPanel" : java.awt.Color( 255, 230, 180 ),
	"elementPrototypeDnDPanel" : java.awt.Color( 255, 255, 255 ),
	"formattedElementViewController" : java.awt.Color( 255, 255, 255 ),
	"response" : java.awt.Color( 255, 230, 180 ),
	"question" : java.awt.Color( 212, 204, 249 ),
	"userDefinedResponse" : java.awt.Color( 255, 230, 180 ),
	"userDefinedQuestion" : java.awt.Color( 212, 204, 249 ),
	"userDefinedQuestionComponent" : java.awt.Color( 255, 230, 180 ),
	"commentForeground" : java.awt.Color( 0, 164, 0 ),
	"variableDnDPanel" : java.awt.Color( 255, 255, 200 ),
	"userDefinedQuestionEditor" : java.awt.Color( 225, 255, 195 ),
	"userDefinedResponseEditor" : java.awt.Color( 255, 255, 210 ),
	"editorHeaderColor" : java.awt.Color( 255, 255, 255 ),
	"behavior" : java.awt.Color( 203, 231, 236 ),
	"behaviorBackground" : java.awt.Color( 255, 255, 255 ),
	"makeSceneEditorBigBackground" : java.awt.Color( 0, 150, 0 ),
	"makeSceneEditorSmallBackground" : java.awt.Color( 0, 150, 0 ),
	"stdErrTextColor" : java.awt.Color( 138, 212, 101 ),
        "mainFontColor" : java.awt.Color(0,0,0),
}


#########################
# Experimental Features
#########################

experimental = 0


####################################
# transfer resource data to Alice
####################################

resourceTransferFile = java.io.File( JAlice.getAliceHomeDirectory(), "resources/common/ResourceTransfer.py" )
execfile( resourceTransferFile.getAbsolutePath() )

