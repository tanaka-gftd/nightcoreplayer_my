/*
  起動時に実行するクラスを jp.ed.nnn.nightcoreplayer.Mainとして、
  JavaFXを利用できるように設定している。
*/


package jp.ed.nnn.nightcoreplayer

//JavaFXで利用するクラスのimport文
//JavaFXのJavaのクラスは、Scalaでも使用できる
import java.io.File
import javafx.application.Application
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.{BorderPane, HBox}
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration

/*
  Mainオブジェクト。
  Scalaのアプリケーションとして実行するために、Appトレイトをミックスインしている。

  また、その中の実装では、JavaFXのApplicationクラスのlaunchメソッドという、static修飾子付きのメソッドを読んでいる。
  launchメソッドの引数には、下の方で記述したMainというクラス(JavaFXアプリケーション)を、
  classOf[Main]という、Scalaでクラスオブジェクトを得るための書き方で渡している。

  更に、AppトレイトのフィールドであるargsというArray[String]型の変数をlaunchメソッドの可変長引数として渡すために、
  args: _* という型を指定した形式で渡している。(cmdキー+returnキーで、launchメソッドの定義元を参照されたし)
*/
object Main extends App {
  Application.launch(classOf[Main], args: _*)
}


//JavaFXアプリケーション
class Main extends Application {

  //JavaFXアプリケーションの構成設定
  override def start(primaryStage: Stage): Unit = {

    //MediaPlayerクラスに、映像ファイルのパスをjava.net.URIクラスに変換＆文字列化したものを渡してインスタンス化
    //Mediaクラス...メディアファイルを表すクラス
    //MediaPlayerクラス...メディアを再生するするクラス
    val path = "/Users/admin/IdeaProjects/nightcoreplayer/video.mp4"
    val media = new Media(new File(path).toURI.toString)
    val mediaPlayer = new MediaPlayer(media)

    //メディアプレイヤーの再生速度を1.25倍にしてから再生
    mediaPlayer.setRate(1.25)   //（これでいいはずなのに、速さが変わらない。何故？）
    mediaPlayer.play()

    //MediaViewクラスに、作成したメディアプレイヤーを渡してインスタンス化
    //MediaViewクラス...実際に映像を表示するクラス
    val mediaView = new MediaView(mediaPlayer)

    //メディアプレイヤーの縦横設定
    mediaView.setFitWidth(800)
    mediaView.setFitHeight(450)

    //Labelクラスのインスタンス作成（このラベルはタイム表示として使用する）
    val timeLabel = new Label()

    mediaPlayer.currentTimeProperty().addListener(new ChangeListener[Duration] {
      override def changed(observable: ObservableValue[_ <: Duration], oldValue: Duration, newValue: Duration): Unit =
        timeLabel.setText(formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration))
    })
    mediaPlayer.setOnReady(new Runnable {
      override def run(): Unit =
        timeLabel.setText(formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration))
    })

    //Labelクラスのインスタンスに文字と文字色を設定
    timeLabel.setText("00:00:00/00:00:00")
    timeLabel.setTextFill(Color.WHITE)

    //HBoxクラスのインスタンスを作成
    //HBoxクラス...単一の水平行に子をレイアウトする
    val toolBar = new HBox(timeLabel)

    //内包する子要素(Labelクラスのインスタンス)の位置を中央に設定
    toolBar.setAlignment(Pos.CENTER)

    //作成したHBoxクラスのインスタンスの背景色を、setStyleメソッドで黒にする
    toolBar.setStyle("-fx-background-color: Black")

    //BorderPaneクラスのインスタンスを生成
    //BorderPaneクラス...レイアウトを行える部品
    val baseBorderPane = new BorderPane()

    //作成したBorderPaneクラスのインスタンスの背景色を、setStyleメソッドで黒にする
    baseBorderPane.setStyle("-fx-background-color: Black")

    //メディアプレイヤーを表示する場所を、setCenterメソッドでBorderPaneクラスの中央に設定
    baseBorderPane.setCenter(mediaView)

    //ツールバーを表示する場所は、BorderPaneクラスの下部に設定
    baseBorderPane.setBottom(toolBar)

    //Sceneクラスのインスタンスを生成
    //Sceneクラス...JavaFXのUIコンポーネントの入れ物、コンテナ
    //コンストラクタ引数として、サイズ設定のほか、Sceneに含めるNodeとしてBorderPaneクラスのインスタンスを渡している
    val scene = new Scene(baseBorderPane, 800, 500)

    //背景色は黒
    scene.setFill(Color.BLACK)

    //StageクラスのインスタンスであるprimaryStageに、上で作成したSceneクラスのインスタンスをセット
    //Stageクラス...JavaFXの最上位のコンテナで、Sceneを格納可能
    primaryStage.setScene(scene)

    //見えるようにする
    primaryStage.show()
  }

  private[this] def formatTime(elapsed: Duration, duration: Duration): String = {
    "%02d:%02d:%02d/%02d:%02d:%02d".format(
      elapsed.toHours.toInt,
      elapsed.toMinutes.toInt % 60,
      elapsed.toSeconds.toInt % 60,
      duration.toHours.toInt,
      duration.toMinutes.toInt % 60,
      duration.toSeconds.toInt % 60,
    )
  }
}

