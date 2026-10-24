package com.kendrick.kendrickcraft;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.loading.FMLPaths;

/** Ordenador decorativo que crea una página HTML local y la abre en el navegador predeterminado. */
public class ComputerBlock extends Block {
    private static final String PAGE = """
<!doctype html>
<html lang="es"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Kendrick PC</title><style>
:root{color-scheme:dark}*{box-sizing:border-box}body{margin:0;font:16px system-ui;background:linear-gradient(135deg,#10172b,#172b46);color:#f4f7ff;min-height:100vh}
header{padding:24px;text-align:center;background:#0b1222;border-bottom:1px solid #354663}h1{margin:0;color:#75e0ff}main{max-width:900px;margin:24px auto;padding:0 16px}
.card{background:#111d31;border:1px solid #304663;border-radius:16px;padding:20px;margin-bottom:18px;box-shadow:0 8px 28px #0004}
form{display:flex;gap:8px}input{min-width:0;flex:1;padding:13px;border-radius:9px;border:1px solid #526782;background:#08101e;color:white}
button,a.btn{cursor:pointer;border:0;border-radius:9px;padding:12px 16px;background:#20b9d8;color:#061521;font-weight:700;text-decoration:none;display:inline-block}
.shortcuts{display:flex;flex-wrap:wrap;gap:10px}.shortcuts a:nth-child(2){background:#ff4d65}.shortcuts a:nth-child(3){background:#8bdc8b}
#arena{height:300px;position:relative;overflow:hidden;background:radial-gradient(#213d59,#08101c);border-radius:12px;cursor:crosshair}
.target{position:absolute;border:0;border-radius:50%;width:44px;height:44px;background:#ffcc4d;box-shadow:0 0 18px #ffcc4d88;color:#172033;font-size:20px}
small{color:#a9bad1}
</style></head><body><header><h1>💻 Kendrick PC</h1><p>Tu ordenador dentro de KendrickCraft</p></header><main>
<section class="card"><h2>🌐 Navegador</h2><form id="search"><input id="address" placeholder="Escribe una dirección web o una búsqueda..." value="https://www.google.com"><button>Abrir</button></form><p><small>Las páginas web se abren en este navegador.</small></p></section>
<section class="card"><h2>⚡ Accesos rápidos</h2><div class="shortcuts"><a class="btn" href="https://www.google.com" target="_blank" rel="noreferrer">Google</a><a class="btn" href="https://www.youtube.com" target="_blank" rel="noreferrer">YouTube</a><a class="btn" href="https://store.steampowered.com" target="_blank" rel="noreferrer">Steam</a></div></section>
<section class="card"><h2>🎯 Minijuego: toca los objetivos</h2><p>Pulsa los círculos amarillos antes de que desaparezcan. Puntos: <b id="score">0</b> · Tiempo: <b id="time">20</b>s</p><button id="start">Empezar partida</button><div id="arena" aria-label="Zona del minijuego"></div><p id="status">¡Dale a empezar!</p></section>
</main><script>
const form=document.querySelector('#search'),address=document.querySelector('#address');
form.addEventListener('submit',e=>{e.preventDefault();let v=address.value.trim();if(!v)return;const url=/^https?:\/\//i.test(v)?v:(v.includes('.')&&!v.includes(' ')?'https://'+v:'https://www.google.com/search?q='+encodeURIComponent(v));window.open(url,'_blank','noopener,noreferrer')});
const arena=document.querySelector('#arena'),scoreEl=document.querySelector('#score'),timeEl=document.querySelector('#time'),statusEl=document.querySelector('#status');let score=0,time=20,timer=null,spawn=null;
function target(){if(!timer)return;const t=document.createElement('button');t.className='target';t.textContent='★';t.style.left=Math.max(0,Math.random()*(arena.clientWidth-44))+'px';t.style.top=Math.max(0,Math.random()*(arena.clientHeight-44))+'px';t.onclick=()=>{score++;scoreEl.textContent=score;t.remove()};arena.append(t);setTimeout(()=>t.remove(),950)}
document.querySelector('#start').onclick=()=>{clearInterval(timer);clearInterval(spawn);arena.innerHTML='';score=0;time=20;scoreEl.textContent=score;timeEl.textContent=time;statusEl.textContent='¡A por los objetivos!';timer=setInterval(()=>{time--;timeEl.textContent=time;if(time<=0){clearInterval(timer);clearInterval(spawn);timer=null;statusEl.textContent='¡Partida terminada! Puntuación: '+score;arena.innerHTML=''}},1000);spawn=setInterval(target,650);target()};
</script></body></html>
""";

    public ComputerBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) return InteractionResult.SUCCESS;
        try {
            Path page = FMLPaths.GAMEDIR.get().resolve("kendrickcraft-computer.html");
            Files.writeString(page, PAGE, StandardCharsets.UTF_8);
            Util.getPlatform().openUri(page.toUri());
            player.displayClientMessage(Component.literal("Abriendo Kendrick PC en tu navegador predeterminado..."), true);
        } catch (IOException | RuntimeException ex) {
            player.displayClientMessage(Component.literal("No se pudo crear la página Kendrick PC: " + ex.getMessage()), false);
        }
        return InteractionResult.SUCCESS;
    }
}
