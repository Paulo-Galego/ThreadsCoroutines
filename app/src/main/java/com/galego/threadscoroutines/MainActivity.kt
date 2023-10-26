package com.galego.threadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.galego.threadscoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAbrir.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
            finish()
        }

        binding.btnIniciar.setOnClickListener {

            //CoroutineScope(Dispatchers.Main).launch {
            //MainScope().launch {
            //CoroutineScope(Dispatchers.IO).launch {
             //GlobalScope.launch {
            // lifecycleScope.launch {
            runBlocking {
                binding.btnIniciar.text ="Executando"
                /*repeat(30){indice ->
                    //binding.btnIniciar.text ="Executando ${indice}"
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")
                    delay(1000L)
                }*/
            }

           /* repeat(15){indice ->
                Log.i("info_thread", "Excutando: $indice T: ${Thread.currentThread().name} $")
                Thread.sleep(1000)
            }*/
           // MinhaThread().start()
           // Thread(MinhaRunnable()).start()
           /* Thread{
                repeat(30){indice ->
                    Log.i("info_thread", "Minha Thread: $indice T: ${Thread.currentThread().name} ")
                    runOnUiThread {
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                        binding.btnIniciar.isEnabled = false
                        if(indice==29){
                            binding.btnIniciar.text = "Reiniciar execução"
                            binding.btnIniciar.isEnabled = true
                        }
                    }
                    Thread.sleep(1000)
                }
            }.start()
            */

          /* job = CoroutineScope(Dispatchers.IO).launch {
               *//* repeat(15){indice ->
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name} $")
                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executou: $indice T: ${Thread.currentThread().name}"
                    }
                    delay(1000)
                }*//*
                //
                *//*withTimeout(7000L){
                    executar()
                }*//*
                  val tempo = measureTimeMillis {
                    val resultado1 = async {tarefa1()}
                    val resultado2 =async {tarefa2()}

                      withContext(Dispatchers.Main){
                          binding.btnIniciar.text = "${resultado1.await()}"
                          binding.btnParar.text = "${resultado2.await()}"

                      }



                  Log.i("info_coroutine", "resultado 1: ${resultado1.await()}")
                  Log.i("info_coroutine", "resultado 1: ${resultado2.await()}")
              }
               Log.i("info_coroutine","Tempo: ${tempo}" )
            }*/





        }

        binding.btnParar.setOnClickListener {
            job?.cancel()
            pararThread = true
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true
        }

    }

    private suspend fun  tarefa1(): String{
        repeat(3){indice ->
            Log.i("info_coroutine", "Tarefa 1: $indice T: ${Thread.currentThread().name} $")
            delay(1000L)//1 segundo
        }
        return "Executou tarefa 1"
    }

    private suspend fun  tarefa2(): String{
        repeat(5){indice ->
            Log.i("info_coroutine", "Tarefa 2: $indice T: ${Thread.currentThread().name} $")
            delay(1000L)//1 segundo
        }
        return "Executou tarefa 2"
    }



    private suspend fun executar(){
        repeat(15){indice ->
            Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name} $")
            withContext(Dispatchers.Main){
                binding.btnIniciar.text = "Executou: $indice T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled=false
            }
            delay(1000L)//1 segundo
        }
    }

    private suspend fun dadosUusuario(){
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine", "Usuarío: ${usuario.nome} T: ${Thread.currentThread().name} $")
        val postagens = RecuperarPostagensPeloId(usuario.id)
        Log.i("info_coroutine", "Postagens: ${postagens.size} T: ${Thread.currentThread().name} $")

    }

    private suspend fun RecuperarPostagensPeloId(idUsuario: Int): List<String>{
        delay(2000)
        return listOf(
            "Viagem Nordeste",
            "Estudando Android",
            "Viagem para disney"
        )

    }

    private suspend fun recuperarUsuarioLogado(): Usuario{
        delay(2000)
        return Usuario(1020, "Paulo Galego")
    }


    inner class MinhaRunnable: Runnable{
        override fun run() {
            repeat(30){indice ->

                if(pararThread) { //Esse return, retorna vazio para o método run, logo ele para a thread
                    pararThread=false
                    return
                }

                Log.i("info_thread", "Minha Thread: $indice T: ${Thread.currentThread().name} ")
                runOnUiThread {
                    binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice==29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)
            }
        }

    }

    inner class MinhaThread : Thread(){

        override fun run() {
            super.run()

            repeat(30){indice ->
                Log.i("info_thread", "Minha Thread: $indice T: ${currentThread().name} ")
                runOnUiThread {
                    binding.btnIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice==29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)
            }


        }
    }
}