################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
Full_Demo/FreeRTOS+CLI/FreeRTOS_CLI.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI/FreeRTOS_CLI.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/FreeRTOS+CLI/FreeRTOS_CLI.pp" --obj_directory="Full_Demo/FreeRTOS+CLI" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


