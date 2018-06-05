import MusicController.{Play, Stop}
import MusicPlayer.{StartMusic, StopMusic}
import akka.actor.{Actor, ActorSystem, Props}

//Music controller messages
object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  val props = Props[MusicController]
}
//music controller
class MusicController extends Actor {
  override def receive: Receive = {
    case Play => println("Music started..")
    case Stop => println("Music stopped...")
  }
}
//Music player messages
object MusicPlayer {
  sealed trait PlayerMsg
  case object StopMusic extends PlayerMsg
  case object StartMusic extends PlayerMsg

  val props = Props[MusicPlayer]
}
//music player
class MusicPlayer extends Actor {
  override def receive: Receive = {
    case StopMusic => println("I don't want to Stop")
    case StartMusic =>
      val controller = context.actorOf(MusicController.props,"controller")
      controller ! Play
  }
}


object ActorCreation extends App {

  val system = ActorSystem("ActorCreation")

  val player = system.actorOf(MusicPlayer.props)

  player ! StartMusic

  player ! StopMusic

  system.terminate()
}
