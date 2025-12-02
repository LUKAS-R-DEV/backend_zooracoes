# Guia de Ícones - Lucide Icons

Este projeto usa **Lucide Icons** através do componente `Icon.vue`.

## Como Usar

```vue
<Icon name="nome-do-icone" :size="20" />
```

## Ícones Mais Usados no Projeto

### Navegação
- `layout-dashboard` - Dashboard
- `users` - Tutores
- `dog` - Pets
- `calendar` - Agendamentos
- `stethoscope` ou `activity` - Serviços

### Ações
- `plus` - Adicionar/Criar
- `pencil` - Editar
- `trash-2` - Excluir
- `eye` - Ver/Visualizar
- `save` - Salvar
- `x` - Cancelar/Fechar
- `arrow-left` - Voltar

### Status
- `check-circle` - Concluído/Sucesso
- `x-circle` - Cancelado/Erro
- `clock` - Agendado/Pendente
- `alert-triangle` - Alerta/Aviso

### Formulários
- `search` - Buscar
- `eye` / `eye-off` - Mostrar/Ocultar senha
- `log-in` - Login
- `log-out` - Logout
- `loader-2` - Carregando

### Médico/Veterinário
- `heart-pulse` ou `activity` - Logo/Sistema
- `syringe` - Vacinas
- `pill` - Prescrições
- `stethoscope` ou `activity` - Serviços médicos

### Outros
- `bell` - Notificações
- `help-circle` - Ajuda
- `settings` - Configurações
- `user` - Perfil
- `chevron-down` - Dropdown

## Lista Completa

Para ver todos os ícones disponíveis, visite: https://lucide.dev/icons/

## Notas

- O componente `Icon` converte automaticamente kebab-case para PascalCase
- Se um ícone não for encontrado, será usado `HelpCircle` como fallback
- Tamanho padrão: 20px
- Stroke width padrão: 1.5
- Cor padrão: `currentColor` (herda a cor do texto)



