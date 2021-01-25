package eu.euipo.europa.cas;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Configuration("somethingConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class SomethingConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Override
    public void configureWebflowExecutionPlan(CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(
                new SomethingWebflowConfigurer(
                        flowBuilderServices,
                        loginFlowDefinitionRegistry,
                        applicationContext,
                        casProperties
                )
        );
    }

    public class SomethingWebflowConfigurer extends AbstractCasWebflowConfigurer {
        public SomethingWebflowConfigurer(
                FlowBuilderServices flowBuilderServices,
                FlowDefinitionRegistry flowDefinitionRegistry,
                ConfigurableApplicationContext applicationContext,
                CasConfigurationProperties casProperties
        ) {
            super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
        }

        @Override
        protected void doInitialize() {
            final Flow loginFlow = super.getLoginFlow();
            // Magic happens; Call 'super' to see what you have access to and alter the flow.
            StateDefinition originalStartState = loginFlow.getStartState();
            originalStartState.getOwner()
        }
    }

}