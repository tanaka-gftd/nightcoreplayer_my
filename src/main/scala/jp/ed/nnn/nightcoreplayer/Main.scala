/*
  起動時に実行するクラスを jp.ed.nnn.nightcoreplayer.Mainとして、
  JavaFXを利用できるように設定している。
*/


package jp.ed.nnn.nightcoreplayer

//JavaFXで利用するクラスのimport文
//JavaFXのJavaのクラスは、Scalaでも使用できる
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.Stage

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
  override  def start(primaryStage: Stage): Unit = {

    //BorderPaneクラスのインスタンスを生成
    val baseBorderPane = new BorderPane()

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
}

