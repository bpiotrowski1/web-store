package pl.bpiotrowski.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.webstore.dto.AddressDto;
import pl.bpiotrowski.webstore.entity.Address;
import pl.bpiotrowski.webstore.entity.OrderHeader;
import pl.bpiotrowski.webstore.entity.User;
import pl.bpiotrowski.webstore.exception.EntityNotFoundException;
import pl.bpiotrowski.webstore.repository.AddressRepository;
import pl.bpiotrowski.webstore.repository.OrderHeaderRepository;
import pl.bpiotrowski.webstore.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final UserRepository userRepository;

    public AddressDto findAddressByOrderId(Long id) {
        AddressDto dto = new AddressDto();
        OrderHeader orderHeader = orderHeaderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address " + id + " not found"));
        Address address = orderHeader.getAddress();

        dto.setFirstName(address.getFirstName());
        dto.setLastName(address.getLastName());
        dto.setStreet(address.getStreet());
        dto.setHouseNumber(address.getHouseNumber());

        return dto;
    }

    public AddressDto findAddressByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        Address entity = user.getAddress() != null ? user.getAddress() : new Address();

        return mapAddressEntityToDto(entity);
    }

    public void create(AddressDto dto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        Address entity = mapAddressDtoToEntity(dto);

        if(user.getAddress() != null) {
            Address actual = user.getAddress();
            user.setAddress(null);
            addressRepository.delete(actual);
        }
        addressRepository.save(entity);
        user.setAddress(entity);
        userRepository.flush();
    }

    private AddressDto mapAddressEntityToDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setFirstName(address.getFirstName());
        dto.setLastName(address.getLastName());
        dto.setStreet(address.getStreet());
        dto.setHouseNumber(address.getHouseNumber());
        return dto;
    }

    private Address mapAddressDtoToEntity(AddressDto dto) {
        Address address = new Address();
        address.setFirstName(dto.getFirstName());
        address.setLastName(dto.getLastName());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        return address;
    }
}
