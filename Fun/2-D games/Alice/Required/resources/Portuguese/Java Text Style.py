
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
	edu.cmu.cs.stage3.alice.core.response.MoveAnimation : "<<<subject>>>.mova( <direction>, <amount> );",
	edu.cmu.cs.stage3.alice.core.response.MoveTowardAnimation : "<<<subject>>>.movaNaDire��oDe( <target>, <amount> );",
	edu.cmu.cs.stage3.alice.core.response.MoveAwayFromAnimation : "<<<subject>>>.afasteDe( <target>, <amount> );",
	edu.cmu.cs.stage3.alice.core.response.TurnAnimation : "<<<subject>>>.gire( <direction>, <amount> );",
	edu.cmu.cs.stage3.alice.core.response.RollAnimation : "<<<subject>>>.role( <direction>, <amount> );",
	edu.cmu.cs.stage3.alice.core.response.MoveAtSpeed : "<<<subject>>>.movaAUmaVelocidade( <direction>, <speed> );",
	edu.cmu.cs.stage3.alice.core.response.TurnAtSpeed : "<<<subject>>>.gireAUmaVelocidade( <direction>, <speed> );",
	edu.cmu.cs.stage3.alice.core.response.RollAtSpeed : "<<<subject>>>.roleAUmaVelocidade( <direction>, <speed> );",
	edu.cmu.cs.stage3.alice.core.response.ResizeAnimation : "<<<subject>>>.redimensione( <amount> );",
	edu.cmu.cs.stage3.alice.core.response.PointAtAnimation : "<<<subject>>>.apontePara( <target> );",
	edu.cmu.cs.stage3.alice.core.response.TurnToFaceAnimation : "<<<subject>>>.gireParaAFace( <target> );",
	edu.cmu.cs.stage3.alice.core.response.TurnAwayFromAnimation : "<<<subject>>>.gireAfastandoDe( <target> );",
	edu.cmu.cs.stage3.alice.core.response.PointAtConstraint : "<<<subject>>>.restringeParaOPontoEm( <target> );",
	edu.cmu.cs.stage3.alice.core.response.GetAGoodLookAtAnimation : "<<<subject>>>.obtenhaUmaBoaVis�o( <target> );",
	edu.cmu.cs.stage3.alice.core.response.StandUpAnimation : "<<<subject>>>.levante();",
	edu.cmu.cs.stage3.alice.core.response.PositionAnimation : "<<<subject>>>.movaPara( <asSeenBy> );",
	edu.cmu.cs.stage3.alice.core.response.PlaceAnimation : "<<<subject>>>.posicione( <amount>, <spatialRelation>, <asSeenBy> );",
	edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation : "<<<subject>>>.orientePara( <asSeenBy> );",
	edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation : "<<<subject>>>.definaOPontoDeVista( <asSeenBy> );",
	edu.cmu.cs.stage3.alice.core.response.PropertyAnimation : "<element>.defina( <propertyName>, <value> );",
	edu.cmu.cs.stage3.alice.core.response.SoundResponse : "<<<subject>>>.reproduzaOSom( <sound> );",
	edu.cmu.cs.stage3.alice.core.response.Wait : "espere( <duration> );",
	edu.cmu.cs.stage3.alice.core.response.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.response.Print : "mostre( <text>, <object> );",
	edu.cmu.cs.stage3.alice.core.response.CallToUserDefinedResponse : "<userDefinedResponse>( <requiredActualParameters> );",
	edu.cmu.cs.stage3.alice.core.response.ScriptResponse : "roteiro( <script> );",
	edu.cmu.cs.stage3.alice.core.response.ScriptDefinedResponse : "respostaDeRoteiroDefinido( <script> );",
	edu.cmu.cs.stage3.alice.core.response.SayAnimation : "<<<subject>>>.diga( <what> );",
	edu.cmu.cs.stage3.alice.core.response.ThinkAnimation : "<<<subject>>>.pense( <what> );",
	edu.cmu.cs.stage3.pratt.maxkeyframing.PositionKeyframeResponse : "executeAnima��oDoQuadroChavePorPosi��o( <subject> );",
	edu.cmu.cs.stage3.pratt.maxkeyframing.QuaternionKeyframeResponse : "executeAnima��oDoQuadroChavePorOrienta��o( <subject> );",
	edu.cmu.cs.stage3.pratt.maxkeyframing.ScaleKeyframeResponse : "executeAnima��oDoQuadroChavePorEscala( <subject> );",
	edu.cmu.cs.stage3.pratt.maxkeyframing.KeyframeResponse : "executeAnima��oDoQuadroChave( <subject> );",
	edu.cmu.cs.stage3.alice.core.response.PoseAnimation : "<<<subject>>>.definaPose( <pose> );",
	edu.cmu.cs.stage3.alice.core.response.Increment : "<<<variable>>>++",
	edu.cmu.cs.stage3.alice.core.response.Decrement : "<<<variable>>>--",

	edu.cmu.cs.stage3.alice.core.response.VehiclePropertyAnimation : "<element>.defina( <propertyName>, <value> );",

	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtBeginning : "<<<list>>>.adicione( 0, <item> );",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtEnd : "<<<list>>>.adicione( <item> );",
	edu.cmu.cs.stage3.alice.core.response.list.InsertItemAtIndex : "<<<list>>>.adicione( <index>, <item> );",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromBeginning : "<<<list>>>.remova( 0 );",
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromEnd : "<<<list>>>.remova�ltimo();", 
	edu.cmu.cs.stage3.alice.core.response.list.RemoveItemFromIndex : "<<<list>>>.remova( <index> );",
	edu.cmu.cs.stage3.alice.core.response.list.Clear : "<<<list>>>.limpe();",

	edu.cmu.cs.stage3.alice.core.response.array.SetItemAtIndex : "<<<array>>>[<index>] = <item>;",

	edu.cmu.cs.stage3.alice.core.response.vector3.SetX : "<<<vector3>>>.definaDist�nciaDireita( <value> )",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetY : "<<<vector3>>>.definaDist�nciaAcima( <value> )",
	edu.cmu.cs.stage3.alice.core.response.vector3.SetZ : "<<<vector3>>>.definaDist�nciaAdiante( <value> )",

	edu.cmu.cs.stage3.alice.core.question.userdefined.CallToUserDefinedQuestion : "<userDefinedQuestion>( <requiredActualParameters> )",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Return : "retorne <<value>>;",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Comment : "// <<text>>",
	edu.cmu.cs.stage3.alice.core.question.userdefined.Print : "mostre( <text>, <object> );",
	edu.cmu.cs.stage3.alice.core.question.userdefined.PropertyAssignment : "<element>.defina( <propertyName>, <value> );",

	edu.cmu.cs.stage3.alice.core.question.PartKeyed : "<<<owner>>>.parteNomeada( <key> )",

	edu.cmu.cs.stage3.alice.core.question.Width : "<<<subject>>>.obtenhaLargura()",
	edu.cmu.cs.stage3.alice.core.question.Height : "<<<subject>>>.obtenhaAltura()",
	edu.cmu.cs.stage3.alice.core.question.Depth : "<<<subject>>>.obtenhaProfundidade()",
	edu.cmu.cs.stage3.alice.core.question.Quaternion : "<<<subject>>>.obtenhaQuaternion()",
	edu.cmu.cs.stage3.alice.core.question.Position : "<<<subject>>>.obtenhaPosi��o()",
	edu.cmu.cs.stage3.alice.core.question.PointOfView : "<<<subject>>>.obtenhaPontoDeVista()",

	edu.cmu.cs.stage3.alice.core.question.Not : "!<a>",
	edu.cmu.cs.stage3.alice.core.question.And : "(<a>&&<b>)",
	edu.cmu.cs.stage3.alice.core.question.Or : "(<a>||<b>)",

	edu.cmu.cs.stage3.alice.core.question.StringConcatQuestion : "<a>+<b>",
	edu.cmu.cs.stage3.alice.core.question.ToStringQuestion : "<what>.paraCadeiaDeCaracteres()",

	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForNumber : "Di�logoN�mero(<question>)",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserYesNo : "Di�logoBooleano(<question>)",
	edu.cmu.cs.stage3.alice.core.question.ask.AskUserForString : "Di�logoCadeiaDeCaracteres(<question>)",

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

	edu.cmu.cs.stage3.alice.core.question.math.Min : "Mat.min( <a>, <b> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Max : "Mat.max( <a>, <b> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Abs : "Mat.abs( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Sqrt : "Mat.sqrt( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Floor : "Mat.floor( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Ceil : "Mat.ceil( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Sin : "Mat.sen( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Cos : "Mat.cos( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Tan : "Mat.tan( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.ASin : "Mat.asen( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.ACos : "Mat.acos( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.ATan : "Mat.atan( <a> )",
	edu.cmu.cs.stage3.alice.core.question.math.ATan2 : "Mat.atan2( <a>, <b> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Pow : "Mat.pow( <a>, <b> )",
	edu.cmu.cs.stage3.alice.core.question.math.Log : "Mat.log natural of <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Exp : "Mat.exp( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.IEEERemainder : "Mat.IEEERemainder( <a>, <b> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Round : "Mat.arredondado( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.ToDegrees : "Mat.paraGraus( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.ToRadians : "Mat.paraRadianos( <a> )", 
	edu.cmu.cs.stage3.alice.core.question.math.SuperSqrt : "superRaizQuadrada( <a>, <b> )", 
	edu.cmu.cs.stage3.alice.core.question.math.Int : "CadeiaDeCaracteres.valorDe( (int) <a> )",

	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromLeftEdge : "Mouse.obtenhaDist�nciaDaMargemEsquerda()", 
	edu.cmu.cs.stage3.alice.core.question.mouse.DistanceFromTopEdge : "Mouse.obtenhaDist�nciaDaMargemDoTopo()", 

	edu.cmu.cs.stage3.alice.core.question.time.TimeElapsedSinceWorldStart : "obtenhaTempoDecorridoDesdeIn�cioDoMundo()", 

	edu.cmu.cs.stage3.alice.core.question.time.Year : "obtenhaAno()", 
	edu.cmu.cs.stage3.alice.core.question.time.MonthOfYear : "obtenhaM�sDoAno()", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfYear : "obtenhaDiaDoAno()", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfMonth : "obtenhaDiaDoM�s()", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeek : "obtenhaDiaDaSemana()", 
	edu.cmu.cs.stage3.alice.core.question.time.DayOfWeekInMonth : "obtenhaDiaDaSemanaNoM�s()", 
	edu.cmu.cs.stage3.alice.core.question.time.IsAM : "�AM()", 
	edu.cmu.cs.stage3.alice.core.question.time.IsPM : "�PM()", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfAMOrPM : "obtenhaHoraAMOuPM()", 
	edu.cmu.cs.stage3.alice.core.question.time.HourOfDay : "obtenhaHoraDoDia()", 
	edu.cmu.cs.stage3.alice.core.question.time.MinuteOfHour : "obtenhaMinutoDaHora()", 
	edu.cmu.cs.stage3.alice.core.question.time.SecondOfMinute : "obtenhaSegundoDoMinuto()", 

	edu.cmu.cs.stage3.alice.core.question.RandomBoolean : "Rand�mico.pr�ximoBooleano()",
	edu.cmu.cs.stage3.alice.core.question.RandomNumber : "Rand�mico.pr�ximoReal()",

	edu.cmu.cs.stage3.alice.core.question.list.Contains : "<list>.cont�m( <item> )",
	edu.cmu.cs.stage3.alice.core.question.list.FirstIndexOfItem : "<list>.�ndiceDe( <item> )",
	edu.cmu.cs.stage3.alice.core.question.list.IsEmpty : "<list>.est�Vazio()",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtBeginning : "<list>[0]",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtEnd : "<list>.obtenha�ltimoItem()",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtIndex : "<list>[<index>]",
	edu.cmu.cs.stage3.alice.core.question.list.ItemAtRandomIndex : "<list>.obtenhaItemRand�mico()",
	edu.cmu.cs.stage3.alice.core.question.list.LastIndexOfItem : "<list>.�ltimo�ndiceDe( <item> )",
	edu.cmu.cs.stage3.alice.core.question.list.Size : "<list>.tamanho()",

	edu.cmu.cs.stage3.alice.core.question.array.ItemAtIndex : "<<<array>>>[<index>]",
	edu.cmu.cs.stage3.alice.core.question.array.Size : "<<<array>>>.comprimento",

	edu.cmu.cs.stage3.alice.core.question.IsAbove : "<<<subject>>>.est�Acima( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsBehind : "<<<subject>>>.est�Atr�s( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsBelow : "<<<subject>>>.est�Abaixo <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsInFrontOf : "<<<subject>>>.est�NaFrenteDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsLeftOf : "<<<subject>>>.est�AEsquerdaDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsRightOf : "<<<subject>>>.est�ADireitaDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsSmallerThan : "<<<subject>>>.�MenorQue( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsLargerThan : "<<<subject>>>.�MaiorQue( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsNarrowerThan : "<<<subject>>>.�MaisEstreitoQue( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsWiderThan : "<<<subject>>>.�MaisAmploQue( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsShorterThan : "<<<subject>>>.�MaisCurtoQue( <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsTallerThan : "<<<subject>>>.�MaisAltoQue( <object> )",
 
	edu.cmu.cs.stage3.alice.core.question.IsCloseTo : "<<<subject>>>.est�Pr�ximoA( <threshold>, <object> )",
	edu.cmu.cs.stage3.alice.core.question.IsFarFrom : "<<<subject>>>.est�LongeDe( <threshold>, <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceTo : "<<<subject>>>.dist�nciaPara( <object> )",

	edu.cmu.cs.stage3.alice.core.question.DistanceToTheLeftOf : "<<<subject>>>.dist�nciaParaAEsquerdaDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceToTheRightOf : "<<<subject>>>.dist�nciaParaADireitaDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceAbove : "<<<subject>>>.dist�nciaAcima( <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceBelow : "<<<subject>>>.dist�nciaAbaixo( <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceInFrontOf : "<<<subject>>>.dist�nciaNaFrenteDe( <object> )",
	edu.cmu.cs.stage3.alice.core.question.DistanceBehind : "<<<subject>>>.dist�nciaAtr�s( <object> )",

	edu.cmu.cs.stage3.alice.core.question.vector3.X : "<<<vector3>>>.obtenhaDist�nciaDireita()",
	edu.cmu.cs.stage3.alice.core.question.vector3.Y : "<<<vector3>>>.obtenhaDist�nciaAcima()",
	edu.cmu.cs.stage3.alice.core.question.vector3.Z : "<<<vector3>>>.obtenhaDist�nciaParaAFrente()",

	edu.cmu.cs.stage3.alice.core.question.PickQuestion : "oQueFoiEscolhido()",

	edu.cmu.cs.stage3.alice.core.question.RightUpForward : "obtenhaVetor( <right>, <up>, <forward> )",

	edu.cmu.cs.stage3.alice.core.question.Pose : "<<<subject>>>.obtenhaPoseAtual()",
}



##################
# Name Config
##################

nameMap = {
	"edu.cmu.cs.stage3.alice.core.response.DoInOrder" : "fa�aEmOrdem",
	"edu.cmu.cs.stage3.alice.core.response.DoTogether" : "fa�aJunto",
	"edu.cmu.cs.stage3.alice.core.response.IfElseInOrder" : "se",
	"edu.cmu.cs.stage3.alice.core.response.LoopNInOrder" : "repita",
	"edu.cmu.cs.stage3.alice.core.response.WhileLoopInOrder" : "enquanto",
	"edu.cmu.cs.stage3.alice.core.response.ForEachInOrder" : "paraTodosEmOrdem",
	"edu.cmu.cs.stage3.alice.core.response.ForEachTogether" : "paraTodosJunto",
	"edu.cmu.cs.stage3.alice.core.response.Print" : "mostre",
	"edu.cmu.cs.stage3.alice.core.response.QuaternionAnimation.quaternion" : "orienta��o de",
	"edu.cmu.cs.stage3.alice.core.response.PointOfViewAnimation.pointOfView" : "ponto de vista de",
	"edu.cmu.cs.stage3.alice.core.response.PositionAnimation.position" : "posi��o de",

	"edu.cmu.cs.stage3.alice.core.question.userdefined.Return" : "devolva",

	"edu.cmu.cs.stage3.alice.core.behavior.WorldStartBehavior" : "Quando o mundo inicia",
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
	"edu.cmu.cs.stage3.alice.core.behavior.MouseNavigationBehavior" : "Deixe <mouse> mover a c�mera",
	"edu.cmu.cs.stage3.alice.core.behavior.MouseLookingBehavior" : "Deixe <mouse> orientar a c�mera",
	"edu.cmu.cs.stage3.alice.core.behavior.SoundMarkerPassedBehavior" : "Quando o som marcado <marker> � tocado",
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
	"edu.cmu.cs.stage3.alice.core.question.IsFarFrom.object" : "dist�ncia a partir",

	"edu.cmu.cs.stage3.alice.scenegraph.renderer.directx7renderer.Renderer" : "DirectX 7",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.openglrenderer.Renderer" : "OpenGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.java3drenderer.Renderer" : "Java3D",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.joglrenderer.Renderer" : "JOGL",
	"edu.cmu.cs.stage3.alice.scenegraph.renderer.nullrenderer.Renderer" : "Nenhum",

	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_GENTLY : "IN�CIO_E_FIM_SUAVEMENTE",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_GENTLY_AND_END_ABRUPTLY : "IN�CIO_SUAVEMENTE_E_FIM_BRUSCAMENTE",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_ABRUPTLY_AND_END_GENTLY : "IN�CIO_BRUSCAMENTE_E_FIM_SUAVEMENTE",
	edu.cmu.cs.stage3.alice.core.style.TraditionalAnimationStyle.BEGIN_AND_END_ABRUPTLY : "IN�CIO_E_FIM_BRUSCAMENTE",

	edu.cmu.cs.stage3.alice.core.Direction.LEFT : "ESQUERDA",
	edu.cmu.cs.stage3.alice.core.Direction.RIGHT : "DIREITA",
	edu.cmu.cs.stage3.alice.core.Direction.UP : "CIMA",
	edu.cmu.cs.stage3.alice.core.Direction.DOWN : "BAIXO",
	edu.cmu.cs.stage3.alice.core.Direction.FORWARD : "FRENTE",
	edu.cmu.cs.stage3.alice.core.Direction.BACKWARD : "ATR�S",

	edu.cmu.cs.stage3.alice.core.SpatialRelation.LEFT_OF : "ESQUERDA_DE",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.RIGHT_OF : "DIREITA_DE",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.ABOVE : "ACIMA",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BELOW : "ABAIXO",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.IN_FRONT_OF : "EM_FRENTE_DE",
	edu.cmu.cs.stage3.alice.core.SpatialRelation.BEHIND : "ATR�S",

	edu.cmu.cs.stage3.alice.core.Dimension.ALL : "TUDO",
	edu.cmu.cs.stage3.alice.core.Dimension.LEFT_TO_RIGHT : "ESQUERDA_PARA_DIREITA",
	edu.cmu.cs.stage3.alice.core.Dimension.TOP_TO_BOTTOM : "TOPO_PARA_BASE",
	edu.cmu.cs.stage3.alice.core.Dimension.FRONT_TO_BACK : "FRENTE_PARA_ATR�S",

	edu.cmu.cs.stage3.alice.core.FogStyle.NONE : "NENHUM",
	edu.cmu.cs.stage3.alice.core.FogStyle.LINEAR : "LINEAR",
	edu.cmu.cs.stage3.alice.core.FogStyle.EXPONENTIAL : "EXPONENCIAL",

	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.SOLID : "S�LIDO",
 	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.WIREFRAME : "REPRESENTA��OARAMADA",
	edu.cmu.cs.stage3.alice.scenegraph.FillingStyle.POINTS : "PONTOS",

	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.NONE : "NENHUM",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.FLAT : "PLANO",
	edu.cmu.cs.stage3.alice.scenegraph.ShadingStyle.SMOOTH : "LISO",

	Boolean.TRUE : "verdadeiro",
	Boolean.FALSE : "falso",

	edu.cmu.cs.stage3.alice.scenegraph.Color.WHITE : "BRANCO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLACK : "PRETO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.RED : "VERMELHO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GREEN : "VERDE",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BLUE : "AZUL",
	edu.cmu.cs.stage3.alice.scenegraph.Color.YELLOW : "AMARELO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PURPLE : "ROXO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.ORANGE : "LARANJA",
	edu.cmu.cs.stage3.alice.scenegraph.Color.PINK : "ROSA",
	edu.cmu.cs.stage3.alice.scenegraph.Color.BROWN : "MARROM",
	edu.cmu.cs.stage3.alice.scenegraph.Color.CYAN : "CIANO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.MAGENTA : "MAGENTA",
	edu.cmu.cs.stage3.alice.scenegraph.Color.GRAY : "CINZA",
	edu.cmu.cs.stage3.alice.scenegraph.Color.LIGHT_GRAY : "CINZA_CLARO",
	edu.cmu.cs.stage3.alice.scenegraph.Color.DARK_GRAY : "CINZA_ESCURO",

	edu.cmu.cs.stage3.util.HowMuch.INSTANCE : "INST�NCIA",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_PARTS : "INST�NCIA_E_PARTES",
	edu.cmu.cs.stage3.util.HowMuch.INSTANCE_AND_ALL_DESCENDANTS : "INST�NCIA_E_TODOS_DESCENDENTES",
}

htmlNameMap = {
	"edu.cmu.cs.stage3.alice.core.Transformable" : "Objeto",
	"edu.cmu.cs.stage3.alice.core.Model" : "Objeto",
	"java.lang.Number" : "N�mero",
	"java.lang.Boolean" : "Booleano",
	"java.lang.String" : "CadeiaDeCaracteres",
	"edu.cmu.cs.stage3.alice.scenegraph.Color" : "Cor",
	"edu.cmu.cs.stage3.alice.core.TextureMap" : "Textura",
	"edu.cmu.cs.stage3.alice.core.Sound" : "Som",
	"edu.cmu.cs.stage3.alice.core.Pose" : "Pose",
	"edu.cmu.cs.stage3.math.Vector3" : "Posi��o",
	"edu.cmu.cs.stage3.math.Quaternion" : "Orienta��o",
	"edu.cmu.cs.stage3.math.Matrix44" : "PontoDeVista",
	"edu.cmu.cs.stage3.alice.core.ReferenceFrame" : "Objeto",
	"edu.cmu.cs.stage3.alice.core.Light" : "Luz",
	"edu.cmu.cs.stage3.alice.core.Direction" : "Dire��o",
	"edu.cmu.cs.stage3.alice.core.Collection" : "[]",
}


####################
# Color Config
####################

colorMap = {
	"disabledHTMLText": java.awt.Color( 200, 200, 200 ),
	"disabledHTML": java.awt.Color( 230, 230, 230 ),
	"DoInOrder" : java.awt.Color( 255, 255, 255 ),
	"DoTogether" : java.awt.Color( 255, 255, 255 ),
	"IfElseInOrder" : java.awt.Color( 255, 255, 255 ),
	"LoopNInOrder" : java.awt.Color( 255, 255, 255 ),
	"WhileLoopInOrder" : java.awt.Color( 255, 255, 255 ),
	"ForEach" : java.awt.Color( 255, 255, 255 ),
	"ForEachInOrder" : java.awt.Color( 255, 255, 255 ),
	"ForAllTogether" : java.awt.Color( 255, 255, 255 ),
	"Wait" : java.awt.Color( 255, 255, 255 ),
	"ScriptResponse" : java.awt.Color( 255, 255, 255 ),
	"ScriptDefinedResponse" : java.awt.Color( 255, 255, 255 ),
	"Print" : java.awt.Color( 255, 255, 255 ),
	"Comment" : java.awt.Color( 255, 255, 255 ),
	"Return" : java.awt.Color( 255, 255, 255 ),
	"PropertyAssignment" : java.awt.Color( 255, 255, 255 ),
	"accessibleMathTile" : java.awt.Color( 255, 255, 255 ),
	"dndHighlight" : java.awt.Color( 255, 255, 255 ),
	"dndHighlight2" : java.awt.Color( 0, 200, 0 ),
	"dndHighlight3" : java.awt.Color( 230, 0, 0 ),
	"propertyViewControllerBackground" : java.awt.Color( 255, 255, 255 ),
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
	"prototypeParameter" : java.awt.Color( 255, 255, 255 ),
	"elementDnDPanel" : java.awt.Color( 255, 230, 180 ),
	"elementPrototypeDnDPanel" : java.awt.Color( 255, 255, 255 ),
	"formattedElementViewController" : java.awt.Color( 255, 255, 255 ),
	"response" : java.awt.Color( 255, 255, 255 ),
	"question" : java.awt.Color( 255, 255, 255 ),
	"userDefinedResponse" : java.awt.Color( 255, 255, 255 ),
	"userDefinedQuestion" : java.awt.Color( 255, 255, 255 ),
	"userDefinedQuestionComponent" : java.awt.Color( 255, 255, 255 ),
	"commentForeground" : java.awt.Color( 0, 164, 0 ),
	"variableDnDPanel" : java.awt.Color( 255, 255, 200 ),
	"userDefinedQuestionEditor" : java.awt.Color( 255, 255, 255 ),
	"userDefinedResponseEditor" : java.awt.Color( 255, 255, 255 ),
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


#########################
# Misc
#########################

miscMap["javaLikeSyntax"] = "true"


####################################
# transfer resource data to Alice
####################################

resourceTransferFile = java.io.File( JAlice.getAliceHomeDirectory(), "resources/common/ResourceTransfer.py" )
execfile( resourceTransferFile.getAbsolutePath() )

