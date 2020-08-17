package org.opensrp.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private Queue queue;

	private static Logger logger = LoggerFactory.getLogger(RabbitMQSender.class.toString());

	public RabbitMQSender(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void send(PlanEvaluatorMessage planEvaluatorMessage) {
		rabbitTemplate.convertAndSend(queue.getName(), planEvaluatorMessage);
//		rabbitTemplate.convertAndSend("", queue.getName(), planEvaluatorMessage, m -> {
//			m.getMessageProperties().setContentType("application/json");
//			return m;
//		});
		logger.info("Send Message : " + planEvaluatorMessage.toString());
	}

	public void send(ResourceEvaluatorMessage resourceEvaluatorMessage) {
		rabbitTemplate.convertAndSend(queue.getName(), resourceEvaluatorMessage);
//		rabbitTemplate.convertAndSend("", queue.getName(), resourceEvaluatorMessage, m -> {
//			m.getMessageProperties().setContentType("application/json");
//			return m;
//		});
		logger.info("Send Message : " + resourceEvaluatorMessage.toString());

	}

	public void setRabbitTemplate(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
}
