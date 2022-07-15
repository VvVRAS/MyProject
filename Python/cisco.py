from netmiko import ConnectHandler

cisco18 = ConnectHandler(
    device_type="cisco_ios",
    host="192.168.1.1",
    username="cisco",
    password="cisco1234",
)

cisco18
cisco18.find_prompt()
output = cisoc18.send_command("show ip int br")
print(output)
