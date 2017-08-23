package handmadeguns;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Multimap;

//import net.minecraftforge.fml.common.FMLCommonHandler;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.common.SidedProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import handmadeguns.gui.HMGInventoryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.GuiIngameForge;


	public class HMGItemGun_AMR extends HMGItemGunBase {
		private float attackDamage;
		private float field_150934_a;
		public static String ads;
		public boolean cook;
		
		public HMGItemGun_AMR(int p, float s, float b, double r, int rt, float at, float cz, float czr, float czs, String sd, String sds, String sdre, 
				boolean rc, int ri, ResourceLocation tx, boolean se, String sco, String aads, String aadsr, String aadss, Item ma, boolean cano) {
			super();
			
			
			this.maxStackSize = 1;
	        this.attackDamage = at;
	        this.field_150934_a = at;
	        //this.retime = 30;
	        this.powor = p;
	        this.speed = s;
	        this.bure = b;
	        this.recoil = r;
	        this.bureads = b/5;
	        this.recoilads = r/2;
	        this.reloadtime = rt;
	        this.scopezoom = cz;
	        this.sound = sd;
	        this.soundre = sdre;
	        this.rendercross = rc;
	        this.right = ri;
	        this.texture = tx;
	        this.semi = se;
	        this.soundco = sco;
	        this.adstexture = aads;
	        this.scopezoombase = cz;
			this.scopezoomred = czr;
			this.scopezoomscope = czs;
			this.soundbase = sd;
			this.soundsu = sds;
			this.adstexturer = aadsr;
			this.adstextures = aadss;
			this.magazine = ma;
			this.canobj = cano;
		}

		public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
		  {
			String powor = String.valueOf(this.powor + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack));
			String speed = String.valueOf(this.speed);
			String bure = String.valueOf(this.bure);
			String recoil = String.valueOf(this.recoil);
			String retime = String.valueOf(this.reloadtime);
            String nokori = String.valueOf(getMaxDamage() - par1ItemStack.getItemDamage());
			
			par3List.add(EnumChatFormatting.RED + "RemainingBullet " + StatCollector.translateToLocal(nokori));
		    par3List.add(EnumChatFormatting.WHITE + "FireDamege " + "+" + StatCollector.translateToLocal(powor));
		    par3List.add(EnumChatFormatting.WHITE + "BlletSpeed " + "+" + StatCollector.translateToLocal(speed));
		    par3List.add(EnumChatFormatting.WHITE + "BlletSpread "+ "+" + StatCollector.translateToLocal(bure));
		    par3List.add(EnumChatFormatting.WHITE + "Recoil " + "+" + StatCollector.translateToLocal(recoil));
		    par3List.add(EnumChatFormatting.YELLOW + "ReloadTime " + "+" + StatCollector.translateToLocal(retime));
		   // par3List.add(EnumChatFormatting.YELLOW + "MagazimeType " + StatCollector.translateToLocal("ARMagazine"));
		    if(!(this.scopezoom == 1.0f)){
				String scopezoom = String.valueOf(this.scopezoom);
				par3List.add(EnumChatFormatting.WHITE + "ScopeZoom " + "x" + StatCollector.translateToLocal(scopezoom));
			}
		    //par3List.add(EnumChatFormatting.WHITE + "FirePowor " + StatCollector.translateToLocal("600"));
		  }
		
		public boolean onEntitySwing(EntityLivingBase entity, ItemStack par1ItemStack){
			EntityPlayer par3EntityPlayer = (EntityPlayer) entity;
			boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
			
	         
			if(this.semi == false){
				NBTTagCompound nbt = par1ItemStack.getTagCompound();
				this.cook = false;
				nbt.setBoolean("Cook",cook);	        			
		        		entity.worldObj.playSoundAtEntity(par3EntityPlayer, this.soundco, 1.0F, 1.0F);
		        		if (!par3EntityPlayer.worldObj.isRemote)
			               {
							if(mod_HandmadeGuns.cfg_canEjectCartridge){
								par3EntityPlayer.worldObj.spawnEntityInWorld(new HMGEntityBulletCartridge(par3EntityPlayer.worldObj,(EntityLivingBase) par3EntityPlayer, 1));
			                    }
			               }
		    }
			return false;
		}
		
		public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	    {
			EntityPlayer entityplayer = (EntityPlayer)entity;
			int s;
			int li = getMaxDamage() - itemstack.getItemDamage();
			boolean lflag = cycleBolt(itemstack);
			boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;
			Item item = itemstack.getItem();
			
			if(flag && world.rand.nextInt(3) == 0 && mod_HandmadeGuns.cfg_Flash){
				int xTile = (int) entityplayer.posX;
	    		int yTile = (int) entityplayer.posY;
	    		int zTile = (int) entityplayer.posZ;
	    		world.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x00);
				world.func_147451_t(xTile - 1, yTile, zTile);
				world.func_147451_t(xTile + 1, yTile, zTile);
				world.func_147451_t(xTile, yTile - 1, zTile);
				world.func_147451_t(xTile, yTile + 1, zTile);
				world.func_147451_t(xTile, yTile, zTile - 1);
				world.func_147451_t(xTile, yTile, zTile + 1);
			}/**/
			
			if (this.recoilre == true && flag ) {
				this.isrecoil = 1;
			}
			if (this.isrecoil == 1 && entity != null && entity instanceof EntityPlayer && flag ) {
				this.isrecoil = 0;
				if (itemstack == entityplayer.getCurrentEquippedItem()) {
					{
						++this.recotime;
						if (this.recotime == 2) {
							this.recotime = 0;
							this.recoilre = false;
						}
					}
				}
			}

			// nbt.setBoolean("recoil", recoilre);

			if (itemstack.getItemDamage() == this.getMaxDamage() && flag) {
				this.isreload = 1;
			}
			if (!world.isRemote) {
				// if(this.isreload == 1 ||GVCItemGunBase.isreload == 1){
				if (this.isreload == 1 && flag) {
					this.isreload = 0;
					if (entity != null && entity instanceof EntityPlayer) {
						if (itemstack == entityplayer.getCurrentEquippedItem()) {
							{
								++this.retime;
								if (this.retime == this.reloadtime) {
									this.retime = 0;
									getReload(itemstack, world, entityplayer);
									this.resc = 0;
								}
							}
						}
					}
				}
			}
				if(itemstack.getItemDamage() == this.getMaxDamage())
				 {if (!world.isRemote)
		            {
					if(this.resc == 0){
		    			 world.playSoundAtEntity(entityplayer, this.soundre, 1.0F, 1.0F);
		    			 this.resc = 1;
		    		 }
		            }
				 }
				if (flag) {
					InventoryPlayer playerInv = entityplayer.inventory;
					inventory = new HMGInventoryItem(playerInv, itemstack);
					inventory.openInventory();

					for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
						ItemStack itemstacki = inventory.getStackInSlot(i1);

						if (i1 == 1) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_reddot) {
								this.scopezoom = this.scopezoomred;
							} else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_scope) {
								this.scopezoom = this.scopezoomscope;
							} else {
								this.scopezoom = this.scopezoombase;
							}
						}
						if (i1 == 2) {
							if (righttype == 1) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_laser) {
								HMGEntityLaser var8 = new HMGEntityLaser(world, (EntityLivingBase) entityplayer, 0.01F);
								if (!world.isRemote) {
									world.spawnEntityInWorld(var8);
								}
							}else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_right) {
								if (!world.isRemote) {
								HMGEntityRight var8 = new HMGEntityRight(world, (EntityLivingBase) entityplayer, 2.0F);
								world.spawnEntityInWorld(var8);
								HMGEntityRight2 var9 = new HMGEntityRight2(world, (EntityLivingBase) entityplayer, 0.01F);
								world.spawnEntityInWorld(var9);
								}
							}
							}
						}
						if (i1 == 3) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_Suppressor) {
								this.sound = this.soundsu;
								this.muzzle = false;
			                }else{
			                	this.sound = this.soundbase;
			                	this.muzzle = true;
			                }
						}
						if (i1 == 4) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemAttachment_grip) {
								
							}else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemGun_GL) {
								
							}else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemGun_SG) {
								
							}
						}
						if (i1 == 5) {
							if (itemstacki != null && itemstacki.getItem() instanceof HMGItemBullet_AP) {
								if (!world.isRemote) {
								this.bullettype = 1;
								}
							}else if (itemstacki != null && itemstacki.getItem() instanceof HMGItemBullet_Frag) {
								if (!world.isRemote) {
									this.bullettype = 2;
									}
							}else{
								if (!world.isRemote) {
									this.bullettype = 0;
									}
							}
						}
					}

				}
			super.onUpdate(itemstack, world, entity, i, flag);
			
			if(this.recoilre == true){
				this.isrecoil = 1;
			}
			if (this.isrecoil == 1 && entity != null && entity instanceof EntityPlayer)
	        {
				this.isrecoil = 0;
				     if(flag){
						 {
				    		 ++this.recotime;
				        	 if(this.recotime == 3){
				        		 this.recotime = 0;
				        		 this.recoilre = false;
				         }}} 
		     }
	    }
		
		@Override
	    public byte getCycleCount(ItemStack pItemstack)
	    {
	        return 1;
	    }
		
		public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4){
			EntityPlayer entityplayer = (EntityPlayer)par3EntityPlayer;
			int s;
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean lflag = cycleBolt(par1ItemStack);
			int j = getMaxItemUseDuration(par1ItemStack) - par4;
			float f = (float)j / 20F;
			f = (f * f + f * 2.0F) / 3F;
			boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
			Item item = par1ItemStack.getItem();
			
			if (var5 || par3EntityPlayer.inventory.hasItem(this.magazine))
	        {
	         if(par1ItemStack.getItemDamage() == this.getMaxDamage())
			 {
	        	 //this.isreload = 1;
	        	 //par2World.playSoundAtEntity(par3EntityPlayer, "gvcguns:gvcguns.reload", 1.0F, 1.0F);
			 }
	        	else	 
			 {
	        		if(this.semi == false){
		        		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		        		boolean coo = nbt.getBoolean("Cook");
		        		if(coo == false)
		        		{
							FireBullet(par1ItemStack,par2World,par3EntityPlayer);
							this.recoilre = true;
							this.cook = true;
							nbt.setBoolean("Cook",cook);
		        		}
		        		}else{
		        			if(this.bullettype == 1){
								FireBulletAP(par1ItemStack, par2World, entityplayer);
								}else if(this.bullettype == 2){
									FireBulletFrag(par1ItemStack, par2World, entityplayer);
								}else{
									FireBullet(par1ItemStack, par2World, entityplayer);
								}
		        			this.recoilre = true;
		        			if (!par3EntityPlayer.worldObj.isRemote)
				               {
								if(mod_HandmadeGuns.cfg_canEjectCartridge){
									par3EntityPlayer.worldObj.spawnEntityInWorld(new HMGEntityBulletCartridge(par3EntityPlayer.worldObj,(EntityLivingBase) par3EntityPlayer, 1));
				                    }
				               }
		        		}
			  }
			}
			
        }
		
		public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	    {
			int s;
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

	        if (var5 || par3EntityPlayer.inventory.hasItem(this.magazine))
	        {
	         if(par1ItemStack.getItemDamage() == this.getMaxDamage())
			 {
			 }
	        	else	 
			 {
	        		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			 }
			}
	        return par1ItemStack;
	    }

		public void FireBullet(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
		{
			par2World.playSoundAtEntity(par3EntityPlayer, this.sound, 1.0F, 1.0F);

            par1ItemStack.damageItem(1, par3EntityPlayer);
            
            double ix = 0;
    		double iz = 0;
    		double iy = par3EntityPlayer.motionY;
    		float f1 = par3EntityPlayer.rotationYawHead * (2 * (float) Math.PI / 360);
    		float f2 = par3EntityPlayer.rotationPitch * (2 * (float) Math.PI / 360);
    		//if (!par3EntityPlayer.isSneaking()) 
    		if (mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
    		{
    		ix -= MathHelper.sin(f1 + 0.3F) * 1.5;
    		iz += MathHelper.cos(f1 + 0.3F) * 1.5;
    		}else{
    			ix -= MathHelper.sin(f1) * 1.5;
    			iz += MathHelper.cos(f1) * 1.5;
    		}
    		Vec3 look = par3EntityPlayer.getLookVec();
    	    double xx = look.xCoord;
    	    double yy = look.yCoord;
    	    double zz = look.zCoord;
    		
    	    HMGEntityParticles var10 = new HMGEntityParticles(par2World, (EntityLivingBase) par3EntityPlayer,
    	    		par3EntityPlayer.posX+ix, par3EntityPlayer.posY+yy+1.2, par3EntityPlayer.posZ+iz);
    	    if (!par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle) {
    			par2World.spawnEntityInWorld(var10);
    		}
    	    if (par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle && mod_HandmadeGuns.cfg_Flash) {
    	    	int xTile = (int) par3EntityPlayer.posX;
        		int yTile = (int) par3EntityPlayer.posY;
        		int zTile = (int) par3EntityPlayer.posZ;
        		par2World.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x99);
    			par2World.func_147451_t(xTile - 1, yTile, zTile);
    			par2World.func_147451_t(xTile + 1, yTile, zTile);
    			par2World.func_147451_t(xTile, yTile - 1, zTile);
    			par2World.func_147451_t(xTile, yTile + 1, zTile);
    			par2World.func_147451_t(xTile, yTile, zTile - 1);
    			par2World.func_147451_t(xTile, yTile, zTile + 1);
    	    }
            
            
            int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
            HMGEntityBullet var8 = new HMGEntityBullet(par2World,(EntityLivingBase) par3EntityPlayer, this.powor+pluspower, this.speed, this.bure);
            //GVCEntityBullettest var8 = new GVCEntityBullettest(par2World,(EntityLivingBase) par3EntityPlayer, this.powor, this.speed, this.bure);
            double re;
            //if(par3EntityPlayer.isSneaking())
            if (mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
            {
            	re = 0.5D;
            }else{
            	re = 1.0D;
            }
            
            par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -3F) * this.recoil*re;
            
            if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
				par2World.createExplosion(par3EntityPlayer, par3EntityPlayer.posX, par3EntityPlayer.posY+1, par3EntityPlayer.posZ, 0.0F, false);
				if (mod_HandmadeGuns.cfg_canEjectCartridge) {
					par2World.spawnEntityInWorld(
							new HMGEntityBulletCartridge(par2World, (EntityLivingBase) par3EntityPlayer, 1));
				}
			}
            
		}
		public void FireBulletAP(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
			par2World.playSoundAtEntity(par3EntityPlayer, this.sound, 1.0F, 1.5F);

			par1ItemStack.damageItem(1, par3EntityPlayer);

			double ix = 0;
			double iz = 0;
			double iy = par3EntityPlayer.motionY;
			float f1 = par3EntityPlayer.rotationYawHead * (2 * (float) Math.PI / 360);
			float f2 = par3EntityPlayer.rotationPitch * (2 * (float) Math.PI / 360);
			//if (!par3EntityPlayer.isSneaking()) 
			if (!mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 	
			{
			ix -= MathHelper.sin(f1 + 0.3F) * 1.5;
			iz += MathHelper.cos(f1 + 0.3F) * 1.5;
			}else{
				ix -= MathHelper.sin(f1) * 1.5;
				iz += MathHelper.cos(f1) * 1.5;
			}
			Vec3 look = par3EntityPlayer.getLookVec();
		    double xx = look.xCoord;
		    double yy = look.yCoord;
		    double zz = look.zCoord;
			
		    HMGEntityParticles var10 = new HMGEntityParticles(par2World, (EntityLivingBase) par3EntityPlayer,
		    		par3EntityPlayer.posX+ix, par3EntityPlayer.posY+yy+1.2, par3EntityPlayer.posZ+iz);
		    if (!par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle) {
				par2World.spawnEntityInWorld(var10);
			}
		    if (par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle && mod_HandmadeGuns.cfg_Flash) {
		    	int xTile = (int) par3EntityPlayer.posX;
	    		int yTile = (int) par3EntityPlayer.posY;
	    		int zTile = (int) par3EntityPlayer.posZ;
	    		par2World.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x99);
				par2World.func_147451_t(xTile - 1, yTile, zTile);
				par2World.func_147451_t(xTile + 1, yTile, zTile);
				par2World.func_147451_t(xTile, yTile - 1, zTile);
				par2World.func_147451_t(xTile, yTile + 1, zTile);
				par2World.func_147451_t(xTile, yTile, zTile - 1);
				par2World.func_147451_t(xTile, yTile, zTile + 1);
		    }
			int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			HMGEntityBullet_AP var8 = new HMGEntityBullet_AP(par2World, (EntityLivingBase) par3EntityPlayer,
					this.powor + pluspower, this.speed, this.bure);
			double re;
			//if (par3EntityPlayer.isSneaking()) 
			if (mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
			{
				re = 0.5D;
			} else {
				re = 1.0D;
				
			}
			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -3F) * this.recoil * re;

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
				par2World.createExplosion(par3EntityPlayer, par3EntityPlayer.posX, par3EntityPlayer.posY+1, par3EntityPlayer.posZ, 0.0F, false);
				if (mod_HandmadeGuns.cfg_canEjectCartridge) {
					par2World.spawnEntityInWorld(
							new HMGEntityBulletCartridge(par2World, (EntityLivingBase) par3EntityPlayer, 1));
				}
			}

		}
		public void FireBulletFrag(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
			par2World.playSoundAtEntity(par3EntityPlayer, this.sound, 1.0F, 1.5F);

			par1ItemStack.damageItem(1, par3EntityPlayer);

			double ix = 0;
			double iz = 0;
			double iy = par3EntityPlayer.motionY;
			float f1 = par3EntityPlayer.rotationYawHead * (2 * (float) Math.PI / 360);
			float f2 = par3EntityPlayer.rotationPitch * (2 * (float) Math.PI / 360);
			//if (!par3EntityPlayer.isSneaking())
			if (!mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
			{
			ix -= MathHelper.sin(f1 + 0.3F) * 1.5;
			iz += MathHelper.cos(f1 + 0.3F) * 1.5;
			}else{
				ix -= MathHelper.sin(f1) * 1.5;
				iz += MathHelper.cos(f1) * 1.5;
			}
			Vec3 look = par3EntityPlayer.getLookVec();
		    double xx = look.xCoord;
		    double yy = look.yCoord;
		    double zz = look.zCoord;
			
		    HMGEntityParticles var10 = new HMGEntityParticles(par2World, (EntityLivingBase) par3EntityPlayer,
		    		par3EntityPlayer.posX+ix, par3EntityPlayer.posY+yy+1.2, par3EntityPlayer.posZ+iz);
		    if (!par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle) {
				par2World.spawnEntityInWorld(var10);
			}
		    if (par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle && mod_HandmadeGuns.cfg_Flash) {
		    	int xTile = (int) par3EntityPlayer.posX;
	    		int yTile = (int) par3EntityPlayer.posY;
	    		int zTile = (int) par3EntityPlayer.posZ;
	    		par2World.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x99);
				par2World.func_147451_t(xTile - 1, yTile, zTile);
				par2World.func_147451_t(xTile + 1, yTile, zTile);
				par2World.func_147451_t(xTile, yTile - 1, zTile);
				par2World.func_147451_t(xTile, yTile + 1, zTile);
				par2World.func_147451_t(xTile, yTile, zTile - 1);
				par2World.func_147451_t(xTile, yTile, zTile + 1);
		    }
			int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			HMGEntityBullet_Frag var8 = new HMGEntityBullet_Frag(par2World, (EntityLivingBase) par3EntityPlayer,
					this.powor + pluspower, this.speed, this.bure);
			double re;
			//if (par3EntityPlayer.isSneaking())
			if (mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
			{
				re = 0.5D;
			} else {
				re = 1.0D;
				
			}
			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -3F) * this.recoil * re;

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
				par2World.createExplosion(par3EntityPlayer, par3EntityPlayer.posX, par3EntityPlayer.posY+1, par3EntityPlayer.posZ, 0.0F, false);
				if (mod_HandmadeGuns.cfg_canEjectCartridge) {
					par2World.spawnEntityInWorld(
							new HMGEntityBulletCartridge(par2World, (EntityLivingBase) par3EntityPlayer, 1));
				}
			}

		}
		public void FireBulletAT(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
			par2World.playSoundAtEntity(par3EntityPlayer, this.sound, 1.0F, 1.5F);

			par1ItemStack.damageItem(1, par3EntityPlayer);

			double ix = 0;
			double iz = 0;
			double iy = par3EntityPlayer.motionY;
			float f1 = par3EntityPlayer.rotationYawHead * (2 * (float) Math.PI / 360);
			float f2 = par3EntityPlayer.rotationPitch * (2 * (float) Math.PI / 360);
			//if (!par3EntityPlayer.isSneaking())
			if (!mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
			{
			ix -= MathHelper.sin(f1 + 0.3F) * 1.5;
			iz += MathHelper.cos(f1 + 0.3F) * 1.5;
			}else{
				ix -= MathHelper.sin(f1) * 1.5;
				iz += MathHelper.cos(f1) * 1.5;
			}
			Vec3 look = par3EntityPlayer.getLookVec();
		    double xx = look.xCoord;
		    double yy = look.yCoord;
		    double zz = look.zCoord;
			
		    HMGEntityParticles var10 = new HMGEntityParticles(par2World, (EntityLivingBase) par3EntityPlayer,
		    		par3EntityPlayer.posX+ix, par3EntityPlayer.posY+yy+1.2, par3EntityPlayer.posZ+iz);
		    if (!par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle) {
				par2World.spawnEntityInWorld(var10);
			}
		    if (par2World.isRemote && mod_HandmadeGuns.cfg_muzzleflash && this.muzzle && mod_HandmadeGuns.cfg_Flash) {
		    	int xTile = (int) par3EntityPlayer.posX;
	    		int yTile = (int) par3EntityPlayer.posY;
	    		int zTile = (int) par3EntityPlayer.posZ;
	    		par2World.setLightValue(EnumSkyBlock.Block, xTile, yTile, zTile, 0x99);
				par2World.func_147451_t(xTile - 1, yTile, zTile);
				par2World.func_147451_t(xTile + 1, yTile, zTile);
				par2World.func_147451_t(xTile, yTile - 1, zTile);
				par2World.func_147451_t(xTile, yTile + 1, zTile);
				par2World.func_147451_t(xTile, yTile, zTile - 1);
				par2World.func_147451_t(xTile, yTile, zTile + 1);
		    }
			int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
			HMGEntityBullet_AT var8 = new HMGEntityBullet_AT(par2World, (EntityLivingBase) par3EntityPlayer,
					this.powor + pluspower, this.speed, this.bure);
			double re;
			//if (par3EntityPlayer.isSneaking())
			if (mod_HandmadeGuns.Key_ADS(par3EntityPlayer)) 
			{
				re = 0.5D;
			} else {
				re = 1.0D;
				
			}
			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -3F) * this.recoil * re;

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(var8);
				par2World.createExplosion(par3EntityPlayer, par3EntityPlayer.posX, par3EntityPlayer.posY+1, par3EntityPlayer.posZ, 0.0F, false);
				if (mod_HandmadeGuns.cfg_canEjectCartridge) {
					par2World.spawnEntityInWorld(
							new HMGEntityBulletCartridge(par2World, (EntityLivingBase) par3EntityPlayer, 1));
				}
			}

		}
		public void getReload(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
		{
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;
			
			        par1ItemStack.damageItem(li, par3EntityPlayer);
					setDamage(par1ItemStack, -this.getMaxDamage());
					if (!linfinity) {
					par3EntityPlayer.inventory.consumeInventoryItem(this.magazine);}
					//par2World.playSoundAtEntity(par3EntityPlayer, "random.click", 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				//	par2World.playSoundAtEntity(par3EntityPlayer, this.soundre, 1.0F, 1.0F);
					//return par1ItemStack;
					if (!par3EntityPlayer.worldObj.isRemote)
		               {
						if(mod_HandmadeGuns.cfg_canEjectCartridge){
							par3EntityPlayer.worldObj.spawnEntityInWorld(new HMGEntityBulletCartridge(par3EntityPlayer.worldObj,(EntityLivingBase) par3EntityPlayer, 5));
		                    }
		               }
		}
    
		public Multimap getItemAttributeModifiers()
	    {
	        Multimap multimap = super.getItemAttributeModifiers();
	        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.attackDamage, 0));
	        return multimap;
	    }
}
