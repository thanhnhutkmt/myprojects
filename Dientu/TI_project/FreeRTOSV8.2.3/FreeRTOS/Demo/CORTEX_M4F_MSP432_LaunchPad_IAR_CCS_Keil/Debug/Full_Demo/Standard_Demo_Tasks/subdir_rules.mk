################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
Full_Demo/Standard_Demo_Tasks/EventGroupsDemo.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/EventGroupsDemo.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/EventGroupsDemo.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/GenQTest.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/GenQTest.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/GenQTest.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/IntQueue.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/IntQueue.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/IntQueue.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/IntSemTest.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/IntSemTest.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/IntSemTest.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/TaskNotify.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/TaskNotify.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/TaskNotify.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/TimerDemo.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/TimerDemo.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/TimerDemo.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/countsem.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/countsem.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/countsem.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/recmutex.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/recmutex.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/recmutex.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/semtest.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/semtest.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/semtest.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

Full_Demo/Standard_Demo_Tasks/sp_flop.obj: C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/Minimal/sp_flop.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP432 Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O0 --opt_for_speed=2 --include_path="C:/ti/ccsv6/ccs_base/arm/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/Full_Demo" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib/inc" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil/driverlib" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS-Plus/Source/FreeRTOS-Plus-CLI" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/Common/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/include" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Source/portable/CCS/ARM_CM4F" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/" --include_path="C:/ti/ccsv6/ccs_base/arm/include/CMSIS" --include_path="C:/Users/Nhut/workspace_v6_1/FreeRTOSV8.2.3/FreeRTOS/Demo/CORTEX_M4F_MSP432_LaunchPad_IAR_CCS_Keil" -g --gcc --define=__MSP432P401R__ --define=USE_CMSIS_REGISTER_FORMAT=1 --define=TARGET_IS_MSP432P4XX --define=ccs --verbose_diagnostics --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --preproc_with_compile --preproc_dependency="Full_Demo/Standard_Demo_Tasks/sp_flop.pp" --obj_directory="Full_Demo/Standard_Demo_Tasks" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


