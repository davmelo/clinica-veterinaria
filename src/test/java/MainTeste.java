import com.clinicaveterinaria.dtos.*;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class    MainTeste {
    public static void main(String[] args) {
        ServidorClinica clinica = ServidorClinica.getInstance();

        ClienteRequisicaoDTO cliente1 = new ClienteRequisicaoDTO(
                1L,
                "João",
                "Silva",
                "12345678900",
                "(81) 99999-1111",
                "joao.silva@email.com",
                "Rua das Flores, 123"
        );

        ClienteRequisicaoDTO cliente2 = new ClienteRequisicaoDTO(
                2L,
                "Maria",
                "Oliveira",
                "23456789011",
                "(81) 99999-2222",
                "maria.oliveira@email.com",
                "Av. Brasil, 456"
        );

        ClienteRequisicaoDTO cliente3 = new ClienteRequisicaoDTO(
                3L,
                "Carlos",
                "Souza",
                "34567890122",
                "(81) 99999-3333",
                "carlos.souza@email.com",
                "Rua do Sol, 789"
        );

        ClienteRequisicaoDTO cliente4 = new ClienteRequisicaoDTO(
                4L,
                "Ana",
                "Lima",
                "45678901233",
                "(81) 99999-4444",
                "ana.lima@email.com",
                "Travessa das Palmeiras, 101"
        );

        ClienteRequisicaoDTO cliente5 = new ClienteRequisicaoDTO(
                5L,
                "Pedro",
                "Ferreira",
                "56789012344",
                "(81) 99999-5555",
                "pedro.ferreira@email.com",
                "Alameda das Acácias, 202"
        );

        clinica.cadastrarCliente(cliente1);
        clinica.cadastrarCliente(cliente2);
        clinica.cadastrarCliente(cliente3);
        clinica.cadastrarCliente(cliente4);
        clinica.cadastrarCliente(cliente5);

        AnimalRequisicaoDTO animal1 = new AnimalRequisicaoDTO(
                1L,
                "12345678900",
                "Tobby",
                "Cachorro",
                "Poodle",
                LocalDate.of(2020, 5, 10),
                7.5,
                "Chip 123"
        );

        AnimalRequisicaoDTO animal2 = new AnimalRequisicaoDTO(
                2L,
                "23456789011",
                "Lulu",
                "Gato",
                "Siamês",
                LocalDate.of(2019, 8, 21),
                3.1,
                "Coleira Azul"
        );

        AnimalRequisicaoDTO animal3 = new AnimalRequisicaoDTO(
                3L,
                "34567890122",
                "Rex",
                "Cachorro",
                "Labrador",
                LocalDate.of(2021, 2, 15),
                14.0,
                "Etiqueta REX2021"
        );

        AnimalRequisicaoDTO animal4 = new AnimalRequisicaoDTO(
                4L,
                "34567890122",
                "Luna",
                "Gato",
                "Persa",
                LocalDate.of(2018, 12, 1),
                4.2,
                "Tattoo LUNA99"
        );

        AnimalRequisicaoDTO animal5 = new AnimalRequisicaoDTO(
                5L,
                "56789012344",
                "Nemo",
                "Peixe",
                "Palhaço",
                LocalDate.of(2023, 1, 1),
                0.25,
                "Aquário 5B"
        );

        clinica.cadastrarAnimal(animal1);
        clinica.cadastrarAnimal(animal2);
        clinica.cadastrarAnimal(animal3);
        clinica.cadastrarAnimal(animal4);
        clinica.cadastrarAnimal(animal5);

        VeterinarioRequisicaoDTO vet1 = new VeterinarioRequisicaoDTO(
                1L,
                "Juliana",
                "Mendes",
                "CRMV-12345",
                "juliana.mendes@vetclinic.com",
                "(81) 98888-1111",
                new ArrayList<>()
        );

        VeterinarioRequisicaoDTO vet2 = new VeterinarioRequisicaoDTO(
                2L,
                "Carlos",
                "Silveira",
                "CRMV-23456",
                "carlos.silveira@vetclinic.com",
                "(81) 98888-2222",
                new ArrayList<>()
        );

        VeterinarioRequisicaoDTO vet3 = new VeterinarioRequisicaoDTO(
                3L,
                "Fernanda",
                "Souza",
                "CRMV-34567",
                "fernanda.souza@vetclinic.com",
                "(81) 98888-3333",
                new ArrayList<>()
        );

        VeterinarioRequisicaoDTO vet4 = new VeterinarioRequisicaoDTO(
                4L,
                "Rodrigo",
                "Lima",
                "CRMV-45678",
                "rodrigo.lima@vetclinic.com",
                "(81) 98888-4444",
                new ArrayList<>()
        );

        VeterinarioRequisicaoDTO vet5 = new VeterinarioRequisicaoDTO(
                5L,
                "Aline",
                "Ferreira",
                "CRMV-56789",
                "aline.ferreira@vetclinic.com",
                "(81) 98888-5555",
                new ArrayList<>()
        );

        clinica.cadastrarVeterinario(vet1);
        clinica.cadastrarVeterinario(vet2);
        clinica.cadastrarVeterinario(vet3);
        clinica.cadastrarVeterinario(vet4);
        clinica.cadastrarVeterinario(vet5);

        DispoAgendaRequisicaoDTO dispoJuliana1 = new DispoAgendaRequisicaoDTO(
                DiaSemana.MONDAY,
                LocalTime.of(14, 0)
        );

        DispoAgendaRequisicaoDTO dispoJuliana2 = new DispoAgendaRequisicaoDTO(
                DiaSemana.MONDAY,
                LocalTime.of(15, 30)
        );

        clinica.adiconarDispoAgenda("CRMV-12345", dispoJuliana1);
        clinica.adiconarDispoAgenda("CRMV-12345", dispoJuliana2);

        AgendamentoRequisicaoDTO agendamento1 = new AgendamentoRequisicaoDTO(
                1L, "12345678900", 1L, "CRMV-12345",
                LocalDateTime.of(2025, 7, 7, 14, 0),
                "Consulta de rotina",
                AgendamentoStatus.PENDENTE
        );
        clinica.cadastrarAgendamento(agendamento1);

        AgendamentoRequisicaoDTO agendamento6 = new AgendamentoRequisicaoDTO(
                2L, "12345678900", 1L, "CRMV-12345",
                LocalDateTime.of(2025, 7, 7, 14, 0),
                "Urgência",
                AgendamentoStatus.PENDENTE
        );
        clinica.cadastrarAgendamento(agendamento6);

        // 5. Criando atendimento a partir do agendamento
        AtendimentoRequisicaoDTO atendimento = new AtendimentoRequisicaoDTO(
                1L, 1L, LocalDateTime.of(2025, 7, 7, 14, 45),new ArrayList<>());
        clinica.cadastrarAtendimento(atendimento);

        clinica.buscarAnimal(1L);
        DispoAgendaRequisicaoDTO dispoCarlos1 = new DispoAgendaRequisicaoDTO(
                DiaSemana.FRIDAY,
                LocalTime.of(10, 30)
        );

//        DispoAgendaRequisicaoDTO dispoCarlos2 = new DispoAgendaRequisicaoDTO(
//                DiaSemana.FRIDAY,
//                LocalTime.of(11, 30)
//        );

        clinica.adiconarDispoAgenda("CRMV-23456", dispoCarlos1);
//        clinica.adiconarDispoAgenda("CRMV-23456", dispoCarlos2);


        AgendamentoRequisicaoDTO agendamento2 = new AgendamentoRequisicaoDTO(
                2L, "56789012344", 5L, "CRMV-23456",
                LocalDateTime.of(2025, 7, 11, 10, 30),
                "Consulta de rotina",
                AgendamentoStatus.AGENDADO
        );
        clinica.cadastrarAgendamento(agendamento2);

        // 5. Criando atendimento a partir do agendamento
        AtendimentoRequisicaoDTO atendimento2 = new AtendimentoRequisicaoDTO(
                2L, 2L, LocalDateTime.of(2025, 7, 7, 14, 45), new ArrayList<>()
        );
        clinica.cadastrarAtendimento(atendimento2);

        System.out.println(clinica.buscarCliente("56789012344"));
        System.out.println(clinica.buscarVeterinario("CRMV-23456"));
        System.out.println(clinica.buscarAgendamento(1L));
        System.out.println(clinica.buscarAtendimento(1L));
    }
    //
}
