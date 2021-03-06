package org.hbs.rezoom.extractor.action.email;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hbs.rezoom.bean.model.IConfiguration;
import org.hbs.rezoom.bean.model.channel.ConfigurationEmail;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.extractor.action.core.InBoxReaderScheduler;
import org.hbs.rezoom.extractor.bo.ExtractorBo;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.EMediaMode;
import org.hbs.rezoom.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InBoxReaderSchedulerEmail implements InBoxReaderScheduler
{
	private static final long	serialVersionUID	= 1958518665419239648L;

	private boolean				isRunning			= false;

	@Autowired
	ExtractorBo					extractorBo;
	
	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	@Override
	@Scheduled(fixedDelayString = "${channel.email.delay}")
	public void scheduleChannel()
	{
		try
		{
			if (!isRunning)
			{
				isRunning = true;
				List<IConfiguration> configList = extractorBo.getConfigurationList(EMedia.Email, EMediaMode.Internal);
				System.out.println("gKafkaProducer :" +gKafkaProducer);
				if (CommonValidator.isListFirstNotEmpty(configList))
				{
					ExecutorService executor = Executors.newFixedThreadPool(configList.size());

					for (IConfiguration iConfig : configList)
					{
						executor.execute(new Runnable() {

							@Override
							public void run()
							{
								ConfigurationEmail config = (ConfigurationEmail) iConfig;
								try
								{
									System.out.println("Started By " + config.getFromId() + " at " + new Date());
									InBoxReaderEmailFactory.getInstance().reader(config).readDataFromChannel(config,gKafkaProducer,extractorBo);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
								finally
								{
									System.out.println("Finished By " + config.getFromId() + " at " + new Date());
								}
							}
						});
					}

					executor.shutdown();
					while ( !executor.isTerminated() )
						;
					System.out.println("Finished all threads");
					
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				System.out.println("New Schedule Time Reached, But Earlier Still Running...");
			}
		}
		finally
		{
			isRunning = false;
		}

	}

}
