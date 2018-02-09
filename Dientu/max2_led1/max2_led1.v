module max2_led1(clk, led1, led2, led3);
 
input clk;
output led1;
output led2;
output led3;
 
reg [22:0] counter;
always @(posedge clk) 
	if(counter==5000000) counter<=0; else counter <= counter+1;
 
reg led1;
reg led2;
reg led3;
 
always @(posedge clk) 
	if(counter==5000000) 
		begin
			led1 <= ~led1;
			led2 <= ~led2;
			led3 <= ~led3;
		end
endmodule