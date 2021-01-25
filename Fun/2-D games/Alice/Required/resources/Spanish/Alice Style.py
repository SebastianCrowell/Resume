
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
	edu.cmu.cs.stage3.alice.core.response.MoveAnimation : "<<<subject>>> mover <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.MoveTowardAnimation : "<<<subject>>> acercarse <<amount>> a <<target>>",
	edu.cmu.cs.stage3.alice.core.response.MoveAwayFromAnimation : "<<<subject>>> alejarse <<amount>> de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAnimation : "<<<subject>>> girar <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.RollAnimation : "<<<subject>>> rodar <<direction>><<amount>>",
	edu.cmu.cs.stage3.alice.core.response.MoveAtSpeed : "<<<subject>>> mover con velocidad <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAtSpeed : "<<<subject>>> girar a la velocidad <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.RollAtSpeed : "<<<subject>>> rodar a la velocidad <<direction>><<speed>>",
	edu.cmu.cs.stage3.alice.core.response.ResizeAnimation : "<<<subject>>> cambiar el tama�o <<amount>>",
	edu.cmu.cs.stage3.alice.core.response.PointAtAnimation : "<<<subject>>> girar apuntar a <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnToFaceAnimation : "<<<subject>>> girar para encarar a  <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAwayFromAnimation : "<<<subject>>> girar para alejarse de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.PointAtConstraint : "<<<subject>>> restringido para apuntar a <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnToFaceConstraint : "<<<subject>>> restringido a encarar <<target>>",
	edu.cmu.cs.stage3.alice.core.response.TurnAwayFromConstraint : "<<<subject>>> restringido para alejarse de <<target>>",
	edu.cmu.cs.stage3.alice.core.response.GetAGoodLookAtAnimation : "<<<subject>>> tomar una buena mirada <<target>>",
	edu.cmu.cs.stage3.alice.core.response.StandUpAnimation : "<<<subject>>> pararse",
	edu.cmu.cs.stage3.alice.core.response.PositionAnimation : "<<<subject>>> mover a<<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PlaceAnimation : "<<<subject>>> colocar <<amount>><<spatialRelation>><<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation : "<<<subject>>> orientar a <<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation : "<<<subject>>> establece el punto de vista <<asSeenBy>>",
	edu.cmu.cs.stage3.alice.core.response.PropertyAnimation : "<<<element>>> establecer <propertyName> a <<value>>",
	edu.cmu.cs.stage3.alice.core.response.SoundResponse : "<<<subject>>> reproducir sonido <<sound>>",
	edu.cmu.cs.stage3.alice.core.response.Wait : "esperar <<duration>>",
	edu.cmu.cs.stage3.alice.core.response.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.response.Print : "imprimir <<text>> <<object>>",
	edu.cmu.cs.stage3.alice.core.response.CallToUserDefinedResponse : "<userDefinedResponse><requiredActualParameters>",
	edu.cmu.cs.stage3.alice.core.response.ScriptResponse : "Gui�n (Script) <<script>>",
	edu.cmu.cs.stage3.alice.core.response.ScriptDefinedResponse : "Respuesta definida por el Gui�n <<script>>",
	edu.cmu.cs.stage3.alice.core.response.SayAnimation : "<<<subject>>> dice <<what>>",
	edu.cmu.cs.stage3.alice.core.response.ThinkAnimation : "<<<subject>>> piensa <<what>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.PositionKeyframeResponse : "position keyframe animation <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.QuaternionKeyframeResponse : "orientation keyframe animation <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.ScaleKeyframeResponse : "scale keyframe animation <<subject>>",
	edu.cmu.cs.stage3.pratt.maxkeyframing.KeyframeResponse : "keyframe animation <<subject>>",
	edu.cmu.cs.stage3.alice.core.response.PoseAnimation : "<<<subject>>> establecer pose <<pose>>",
	edu.cmu.cs.stage3.alice.core.response.Increment : "aumentar  <<<variable>>> de 1 en 1",
	edu.cmu.cs.stage3.alice.core.response.Decrement : "disminuir <<<variable>>> de 1 en 1",

	edu.cmu.cs.stage3.alice.core.response.VehiclePropertyAnimation : "<element> establecer <propertyName> con valor <value>",

	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtBeginning : "insertar <item> al principio de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtEnd : "insertar <item> al final de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtIndex : "insertar <item> en la posici�n <index> de <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromBeginning : "eliminar elemento del principio <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromEnd : "eliminar elemento del final<<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromIndex : "eliminar elemento con �ndice <index> en <<<list>>>",
	edu.cmu.cs.stage3.alice.core.response.list.Clear : "eliminar todos elementos en <<<list>>>",

	edu.cmu.cs.stage3.alice.core.response.array.SetItemAtIndex : "establecer elemento en posici�n <index> con <item> en <<<array>>>",

	edu.cmu.cs.stage3.alice.core.response.vector3.SetX : "valor <<value>> para la distancia a la derecha del <<<vector3>>>",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetY : "valor <<value>> para la distancia arriba del <<<vector3>>>",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetZ : "valor <<value>> para la distancia adelante del <<<vector3>>>",

	edu.cmu.cs.stage3.alice.core.question.userdefined.CallToUserDefinedQuestion : "<userDefinedQuestion><requiredActualParameters>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Return : "regresar <<value>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Print : "imprimir <<text>> <<object>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.PropertyAssignment : "el elemento <element> usa el valor <value> para establecer la propiedad <propertyName>",

	edu.cmu.cs.stage3.alice.core.question.PartKeyed : "parte <<<owner>>> se llamada <key>",

	edu.cmu.cs.stage3.alice.core.question.Width : "el ancho del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Height : "la altura del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Depth : "la profundidad del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Quaternion : "cauternio <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.Position : "la posici�n del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.PointOfView : "punto de vista <<<subject>>>",

	edu.cmu.cs.stage3.alice.core.question.Not : "no <a>",
	edu.cmu.cs.stage3.alice.core.question.And : "ambos <a> y <b>",
	edu.cmu.cs.stage3.alice.core.question.Or : "ya sea <a> o <b>, o ambos",

	edu.cmu.cs.stage3.alice.core.question.StringConcatQuestion : "<a> unido con <b>",
	edu.cmu.cs.stage3.alice.core.question.ToStringQuestion : "<what> como secuencia de caracteres (string)",

	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForNumber : "preguntar al usuario un n�mero <<question>>",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserYesNo : "preguntar al usuario s� o no <<question>>",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForString : "preguntar al usuario una secuencia de caracteres (string) <<question>>",

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

	edu.cmu.cs.stage3.alice.core.question.math.Min : "valor m�nimo de <a> y <b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Max : "valor m�ximo de <a>  y <b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Abs : "valor absoluto de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Sqrt : "la ra�z cuadrada de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Floor : "valor m�nimo de <a> como n�mero entero", 
	edu.cmu.cs.stage3.alice.core.question.math.Ceil : "valor m�ximo de <a> como n�mero entero", 
	edu.cmu.cs.stage3.alice.core.question.math.Sin : "seno <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Cos : "coseno <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Tan : "tangente <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ASin : "arco seno<a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ACos : "arco coseno <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.ATan : "arco tangente <a>",
	edu.cmu.cs.stage3.alice.core.question.math.ATan2 : "arco tangente <a><b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Pow : "<a> elevado a la potencia de <b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Log : "logaritmo natural de <a>", 
	edu.cmu.cs.stage3.alice.core.question.math.Exp : "n�mero e elevado a la potencia de <a> power", 
	edu.cmu.cs.stage3.alice.core.question.math.IEEERemainder : "el resto de IEEE<a>/<b>", 
	edu.cmu.cs.stage3.alice.core.question.math.Int : "int <a>",
	edu.cmu.cs.stage3.alice.core.question.math.Round : "redondear <a>",
	edu.cmu.cs.stage3.alice.core.question.math.ToDegrees : "<a> de radianes a grados", 
	edu.cmu.cs.stage3.alice.core.question.math.ToRadians : "<a> de grados a radianes", 
	edu.cmu.cs.stage3.alice.core.question.math.SuperSqrt : "la ra�z <b> de <a>",

	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromLeftEdge : "distancia del rat�n al borde izquierdo", 
	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromTopEdge : "distancia del rat�n al borde superior", 

	edu.cmu.cs.stage3.alice.core.question.time.TimeElapsedSinceWorldStart : "tiempo transcurrido", 

	edu.cmu.cs.stage3.alice.core.question.time.Year : "a�o", 
	edu.cmu.cs.stage3.alice.core.question.time.MonthOfYear : "mes del a�o", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfYear : "d�a del a�o", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfMonth : "d�a del mes", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeek : "d�a de la semana", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeekInMonth : "d�a de la semana en el mes", 
	edu.cmu.cs.stage3.alice.core.question.time.IsAM : "es AM", 
	edu.cmu.cs.stage3.alice.core.question.time.IsPM : "es PM", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfAMOrPM : "hora de AM o PM", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfDay : "hora del d�a", 
	edu.cmu.cs.stage3.alice.core.question.time.MinuteOfHour : "minuto de la hora", 
	edu.cmu.cs.stage3.alice.core.question.time.SecondOfMinute : "segundo del minuto", 

	edu.cmu.cs.stage3.alice.core.question.RandomBoolean : "escoger verdadero <probabilityOfTrue> del tiempo",
	edu.cmu.cs.stage3.alice.core.question.RandomNumber : "n�mero aleatorio",

	edu.cmu.cs.stage3.alice.core.question.list.Contains : "<list> contiene <item>",
	edu.cmu.cs.stage3.alice.core.question.list.FirstIndexOfItem : "el primer �ndice de <item> en <list>",
	edu.cmu.cs.stage3.alice.core.question.list.IsEmpty : " <list> no tiene ning�n valor",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtBeginning : "el primer elemento en <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtEnd : "el �ltimo elemento en <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtIndex : "elemento <index> en <list>",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtRandomIndex : "elemento al azar en <list>",
	edu.cmu.cs.stage3.alice.core.question.list.LastIndexOfItem : "�ltimo �ndice de <item> en<list>",
	edu.cmu.cs.stage3.alice.core.question.list.Size : "tama�o de <list>",

	edu.cmu.cs.stage3.alice.core.question.array.ItemAtIndex : "elemento <index> en <<<array>>>",
	edu.cmu.cs.stage3.alice.core.question.array.Size : "tama�o de <<<array>>>",

	edu.cmu.cs.stage3.alice.core.question.IsAbove : "<<<subject>>> est� por encima de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsBehind : "<<<subject>>> est� detr�s de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsBelow : "<<<subject>>> est� por denajo de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsInFrontOf : "<<<subject>>> est� en frente de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsLeftOf : "<<<subject>>> est� a la izquierda de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsRightOf : "<<<subject>>> est� a la derecha de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsSmallerThan : "<<<subject>>> es m�s peque�o que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsLargerThan : "<<<subject>>> es m�s grande que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsNarrowerThan : "<<<subject>>> es m�s angosto que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsWiderThan : "<<<subject>>> es m�s ancho que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsShorterThan : "<<<subject>>> es m�s bajo que <<object>>",
	edu.cmu.cs.stage3.alice.core.question.IsTallerThan : "<<<subject>>> es m�s alto que <<object>>",
 
	edu.cmu.cs.stage3.alice.core.question.IsCloseTo : "<<<subject>>> est� dentro del rango <threshold> del <object>",
	edu.cmu.cs.stage3.alice.core.question.IsFarFrom : "<<<subject>>> est� como m�nimo <threshold> alejado del <object>",
	edu.cmu.cs.stage3.alice.core.question.DistanceTo : "<<<subject>>> distancia a<<object>>",

	edu.cmu.cs.stage3.alice.core.question.DistanceToTheLeftOf : "<<<subject>>> distancia a la izquierda de <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceToTheRightOf : "<<<subject>>> distancia a la derecha de  <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceAbove : "<<<subject>>> distancia desde arriba <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceBelow : "<<<subject>>> distancia desde abajo <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceInFrontOf : "<<<subject>>> distance en frente de  <<object>>",
	edu.cmu.cs.stage3.alice.core.question.DistanceBehind : "<<<subject>>> distancia detr�s de <<object>>",

	edu.cmu.cs.stage3.alice.core.question.vector3.X : "distancia a la derecha de <<<vector3>>>",
	edu.cmu.cs.stage3.alice.core.question.vector3.Y : "distancia de <<<vector3>>> hasta",
	edu.cmu.cs.stage3.alice.core.question.vector3.Z : "distancia en frente de <<<vector3>>>",

	edu.cmu.cs.stage3.alice.core.question.PickQuestion : "objeto debajo del marcador del rat�n",

	edu.cmu.cs.stage3.alice.core.question.RightUpForward : "<right>, <up>, <forward>",

	edu.cmu.cs.stage3.alice.core.question.Pose : "la pose actual del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.question.PropertyValue : "<<<element>>>.<propertyName>",

	edu.cmu.cs.stage3.alice.core.question.visualization.model.Item : "el valor de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.model.SetItem : "permitir <<<subject>>> = <item>",

	edu.cmu.cs.stage3.alice.core.question.visualization.array.ItemAtIndex : "el valor en <<<subject>>>[ <index> ]",
	edu.cmu.cs.stage3.alice.core.response.visualization.array.SetItemAtIndex : "permitir <<<subject>>>[ <index> ] = <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.array.Size : "<<<subject>>> tiene tama�o",

	edu.cmu.cs.stage3.alice.core.question.visualization.list.Size : "<<<subject>>> tiene tama�o",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.Contains : "<<<subject>>> contiene <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.IsEmpty : "<<<subject>>> no tiene valor",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.FirstIndexOfItem : "el primer �ndice de <<<subject>>> es <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.LastIndexOfItem : "el �ltimo �ndice de <<<subject>>> es <item>",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtBeginning : "el elemento de <<<subject>>> est� al principio",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtEnd : "el elemento de <<<subject>>> est� al final",
	edu.cmu.cs.stage3.alice.core.question.visualization.list.ItemAtIndex : "el elemento <<<subject>>> en el �ndice <index>",
	
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtBeginning : "agregar <item> al principio de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtEnd : "agregar <item> al final de <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.InsertItemAtIndex : "a�adir el <item> en el �nidce <index> del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromBeginning : "eliminar este elemento del principio del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromEnd : "eliminar este elemento del principio del final del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.RemoveItemFromIndex : "eliminar este elemento a partir del �ncice <index> del <<<subject>>>",
	edu.cmu.cs.stage3.alice.core.response.visualization.list.Clear : "borrar el <<<subject>>>",
}



##################
# Name Config
##################

nameMap = {
	"edu.cmu.cs.stage3.alice.core.response.DoInOrder" : "hacer en orden",
	"edu.cmu.cs.stage3.alice.core.response.DoTogether" : "hacer juntos",
	"edu.cmu.cs.stage3.alice.core.response.IfElseInOrder" : "if/else",
	"edu.cmu.cs.stage3.alice.core.response.LoopNInOrder" : "lazo",
	"edu.cmu.cs.stage3.alice.core.response.WhileLoopInOrder" : "while",
	"edu.cmu.cs.stage3.alice.core.response.ForEachInOrder" : "para todos en orden",
	"edu.cmu.cs.stage3.alice.core.response.ForEachTogether" : "para todos juntos",
	"edu.cmu.cs.stage3.alice.core.response.Print" : "imprimir",
	"edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation.quaternion" : "compensado por",
	"edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation.pointOfView" : "punto de vista de",
	"edu.cmu.cs.stage3.alice.core.response.PositionAnimation.position" : "posicion de",

	"edu.cmu.cs.stage3.alice.core.question.userdefined.Return" : "regresar",

	"edu.cmu.cs.stage3.alice.core.behavior.WorldStartBehavior" : "Cuando el Mundo comienza",
	"edu.cmu.cs.stage3.alice.core.behavior.WorldIsRunningBehavior" : "Cuando el Mundo se est� ejecutando",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyClickBehavior" : "Cuando se presiona <keyCode>",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyIsPressedBehavior" : "Mientras <keyCode> es apretado",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonClickBehavior" : "Cuando se hace clic con el <mouse> en <onWhat>",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonIsPressedBehavior" : "Mientras el bot�n del rat�n <mouse> se aprieta en <onWhat>",
	"edu.cmu.cs.stage3.alice.core.behavior.ConditionalBehavior" : "Mientras <condition> sea verdadero",
	"edu.cmu.cs.stage3.alice.core.behavior.ConditionalTriggerBehavior" : "Cuando <condition> se convierte en verdadero",
	"edu.cmu.cs.stage3.alice.core.behavior.VariableChangeBehavior" : "Cuando <variable> cambia",
	"edu.cmu.cs.stage3.alice.core.behavior.MessageReceivedBehavior" : "Cuando un mensaje es enviado de <fromWho> a <toWhom>", 
	"edu.cmu.cs.stage3.alice.core.behavior.DefaultMouseInteractionBehavior" : "Permitir que el <mouse> mueva <objects>",
	"edu.cmu.cs.stage3.alice.core.behavior.KeyboardNavigationBehavior" : "Permitir que <arrowKeys> mueva <subject>",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseNavigationBehavior" : "Permitir que el <mouse> mueva la c�mara",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseLookingBehavior" : "Permitir que el <mouse> oriente la c�mara",
	"edu.cmu.cs.stage3.alice.core.behavior.SoundMarkerPassedBehavior" : "Cuando el sonido <marker> se reproduce",
	"edu.cmu.cs.stage3.alice.core.behavior.SoundLevelBehavior" : "Cuando el nivel de grabaci�n del sonido es >= <level>",

	"edu.cmu.cs.stage3.alice.core.Model.opacity" : "opacidad",
	"edu.cmu.cs.stage3.alice.core.Model.diffuseColorMap" : "textura de la piel",
	"diffuseColorMap" : "textura de la piel",
	"edu.cmu.cs.stage3.alice.core.Transformable.localTransformation" : "cuaternion",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonClickBehavior.onWhat" : "encimaDeQu�",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseButtonIsPressedBehavior.onWhat" : "encimaDeQu�",
	"edu.cmu.cs.stage3.alice.core.question.IsCloseTo.threshold" : "est� dentro de",
	"edu.cmu.cs.stage3.alice.core.question.IsFarFrom.threshold" : "es por lo menos",
	"edu.cmu.cs.stage3.alice.core.question.IsCloseTo.object" : "de",
	"edu.cmu.cs.stage3.alice.core.question.IsFarFrom.object" : "lejos de",

	"edu.cmu.cs.stage3.alice.scenegraph.renderer.directx7renderer.Renderer" : "DirectX 7",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.openglrenderer.Renderer" : "OpenGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.java3drenderer.Renderer" : "Java3D",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.joglrenderer.Renderer" : "JOGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.nullrenderer.Renderer" : "Ninguno",

	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_GENTLY : "comenzar y terminar suavemente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_GENTLY_AND_END_ABRUPTLY : "comenzar y terminar abruptmente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_ABRUPTLY_AND_END_GENTLY : "comenzr abruptamenta y terminar suavemente",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_ABRUPTLY : "comenzar y terminar abruptamente",

	edu.cmu.cs.stage3.alice.core.Direction.LEFT : "izquierda",
	edu.cmu.cs.stage3.alice.core.Direction.RIGHT : "derecha",
	edu.cmu.cs.stage3.alice.core.Direction.UP : "arriba",
	edu.cmu.cs.stage3.alice.core.Direction.DOWN : "abajo",
	edu.cmu.cs.stage3.alice.core.Direction.FORWARD : "adelante",
	edu.cmu.cs.stage3.alice.core.Direction.BACKWARD : "atr�s",

	edu.cmu.cs.stage3.alice.core.SpatialRelation.LEFT_OF : "a la izquierda de",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.RIGHT_OF : "a la derecha de",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.ABOVE : "encima",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BELOW : "debajo",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.IN_FRONT_OF : "en frente de",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BEHIND : "detr�s",

	edu.cmu.cs.stage3.alice.core.Dimension.ALL : "todo",
	edu.cmu.cs.stage3.alice.core.Dimension.LEFT_TO_RIGHT : "de izquierda a derecha",
	edu.cmu.cs.stage3.alice.core.Dimension.TOP_TO_BOTTOM : "de arriba hacia abajo",
	edu.cmu.cs.stage3.alice.core.Dimension.FRONT_TO_BACK : "de adelante hacia atr�s",

	edu.cmu.cs.stage3.alice.core.FogStyle.NONE : "ninguno",
	edu.cmu.cs.stage3.alice.core.FogStyle.LINEAR : "linear",
	edu.cmu.cs.stage3.alice.core.FogStyle.EXPONENTIAL : "exponential",

	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.SOLID : "s�lido",
 	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.WIREFRAME : "alambre",
	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.POINTS : "puntos",

	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.NONE : "ninguno",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.FLAT : "plano",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.SMOOTH : "suave",

	java.lang.Boolean : "Booleano",
	java.lang.Number : "N�mero",
	edu.cmu.cs.stage3.alice.core.Model : "Objeto",

	Boolean.TRUE : "verdadero",
	Boolean.FALSE : "falso",

	edu.cmu.cs.stage3.alice.scenegraph.Color.WHITE : "blanco",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLACK : "negro",
	edu.cmu.cs.stage3.alice.scenegraph.Color.RED : "rojo",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GREEN : "verde",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLUE : "azul",
	edu.cmu.cs.stage3.alice.scenegraph.Color.YELLOW : "amarillo",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PURPLE : "morado",
	edu.cmu.cs.stage3.alice.scenegraph.Color.ORANGE : "anaranjado",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PINK : "rosado",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BROWN : "marr�n",
	edu.cmu.cs.stage3.alice.scenegraph.Color.CYAN : "aguamarina",
	edu.cmu.cs.stage3.alice.scenegraph.Color.MAGENTA : "p�rpura",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GRAY : "gris",
	edu.cmu.cs.stage3.alice.scenegraph.Color.LIGHT_GRAY : "gris claro",
	edu.cmu.cs.stage3.alice.scenegraph.Color.DARK_GRAY : "gris oscuro",

	edu.cmu.cs.stage3.util.HowMuch.INSTANCE : "la instancia",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_PARTS : "la instancia y las partes",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_ALL_DESCENDANTS : "lainstancia y todos los descendientes",
}

htmlNameMap = {
	"edu.cmu.cs.stage3.alice.core.Transformable" : "[Obj]",
	"edu.cmu.cs.stage3.alice.core.Model" : "[Obj]",
	"java.lang.Number" : "[123]",
	"java.lang.Boolean" : "[T/F]",
	"java.lang.String" : "[ABC]",
	"edu.cmu.cs.stage3.alice.scenegraph.Color" : "[Color]",
	"edu.cmu.cs.stage3.alice.core.TextureMap" : "[Textura]",
	"edu.cmu.cs.stage3.alice.core.Sound" : "[Sonido]",
	"edu.cmu.cs.stage3.alice.core.Pose" : "[Pose]",
	"edu.cmu.cs.stage3.math.Vector3" : "[Posicion]",
	"edu.cmu.cs.stage3.math.Quaternion" : "[Ori]",
	"edu.cmu.cs.stage3.math.Matrix44" : "[POV]",
	"edu.cmu.cs.stage3.alice.core.ReferenceFrame" : "[Objeto]",
	"edu.cmu.cs.stage3.alice.core.Light" : "[Luz]",
	"edu.cmu.cs.stage3.alice.core.Direction" : "[Direcci�n]",
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
